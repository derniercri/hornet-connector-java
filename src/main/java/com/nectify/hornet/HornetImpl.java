//
// Copyright 2011 Nectify
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.nectify.hornet;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class HornetImpl implements Hornet
{
    private JedisPool pool;

    private int tokenTTL;

    public HornetImpl(String host, Integer port, Integer timeout)
    {
    	JedisPoolConfig poolConfig = new JedisPoolConfig();
    	//test if the connection is still open
    	//otherwise reopen it!
        poolConfig.setTestOnBorrow(true);
        
        if (host == null) host = "localhost";

        if (port == null)
            pool = new JedisPool(poolConfig, host);

        else if (timeout == null)
            pool = new JedisPool(poolConfig, host, port);

        else
            pool = new JedisPool(poolConfig, host, port, timeout);

        tokenTTL = 120;
    }


    public String createAccessToken(String... channels)
    {
        long timestamp = Math.round(System.currentTimeMillis() / 1000);

        String suffix = StringUtils.leftPad("" + timestamp, 10, '0')
                + StringUtils.leftPad("" + (new Random()).nextInt(10000), 5, '0');

        Jedis jedis = pool.getResource();

        String token = null;
        
        try
        {
            long nextTokenId = jedis.incr("hornet:tokens_id");

            Long tokenBase10 = new Long(nextTokenId + suffix);
            token = HornetUtils.converter(62, tokenBase10);

            String key = "hornet:token:" + token;

            for (int i = 0; i < channels.length; i++)
            {
                String channel = channels[i];

                jedis.sadd(key, channel);
            }
            
            jedis.expire(key, tokenTTL);
        }
        finally
        {
            pool.returnResource(jedis);
        }

        return token;
    }

    public void disconnectTokens(String... tokens)
    {
        JSONObject disconnectMsg = new JSONObject();

        JSONArray tokensToDisconnect = new JSONArray((Object[]) tokens);

        disconnectMsg.accumulate("tokens", tokensToDisconnect);

        this.publish("hornet", "disconnect_tokens", disconnectMsg);
    }

    public void subscribe(String event, HornetSubscriber subscriber)
    {
        Jedis jedis = pool.getResource();

        try
        {
            jedis.subscribe(subscriber, event);
        }
        finally
        {
            pool.returnResource(jedis);
        }

    }

    public void publish(String[] channels, String type, JSONObject message, Object... options)
    {
        for (int i = 0; i < channels.length; i++)
        {
            String channel = channels[i];
            publish(channel, type, message, options);
        }
    }

    public Long publish(String channel, String type, JSONObject message, Object... options)
    {
        int i = 0;

        int length = options.length;

        if (length % 2 != 0)
            throw new IllegalArgumentException("Options should be in a key/value form.");

        if (message == null) message = new JSONObject();

        while (i < length)
        {
            String name = options[i++].toString();
            Object value = options[i++];

            if (value == null) continue;

            message.put(name, value.toString());
        }

        message.put("type", type);

        Jedis jedis = pool.getResource();
        Long result;

        try
        {
            result = jedis.publish("hornet:channel:" + channel, message.toCompactString());
        }
        finally
        {
            pool.returnResource(jedis);
        }
        
        return result;
    }

    public JedisPool getJedisPool()
    {
        return pool;
    }

    public int getTokenTTL()
    {
        return tokenTTL;
    }

    public void setTokenTTL(int seconds)
    {
        tokenTTL = seconds;
    }
}
