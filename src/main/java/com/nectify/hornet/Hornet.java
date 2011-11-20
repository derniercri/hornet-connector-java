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

import org.apache.tapestry5.json.JSONObject;

import redis.clients.jedis.JedisPool;

public interface Hornet
{
    public final static String EVENT_CONNECT = "hornet:events:connect";

    public final static String EVENT_DISCONNECT = "hornet:events:disconnect";

    public String createAccessToken(String channel);

    public void disconnectTokens(String... tokens);
    
    public void publish(String[] channels, String type, JSONObject message, Object... options);

    public Long publish(String channel, String type, JSONObject message, Object... options);

    public void subscribe(String event, HornetSubscriber handler);

    public JedisPool getJedisPool();

    public void setTokenTTL(int seconds);

    public int getTokenTTL();
}
