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

    /**
     * Create a token in order to provide access for an user to a list of channels.
     * -
     * -
     * tokens are the base62 version of the concatenation (string) of :
     * - unique id on any number of digits
     * - current timestamp (in seconds) left padded to be on 10 digits. i.e. it means any
     * timestamp will look like : 0001234953. 10 digits are enough to go to Sat Nov 20 18:46:39
     * +0100 2286 (9999999999)
     * - a random number on 5 digits, again, left padded with 0. This last
     * part is just to increase the complexity of the token.
     * 
     * @return
     */
    public String createAccessToken(String... channels);

    public void disconnectTokens(String... tokens);

    public void publish(String[] channels, String type, JSONObject message, Object... options);

    public Long publish(String channel, String type, JSONObject message, Object... options);

    public void subscribe(String event, HornetSubscriber handler);

    public JedisPool getJedisPool();

    public void setTokenTTL(int seconds);

    public int getTokenTTL();
}
