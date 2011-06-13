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

import redis.clients.jedis.JedisPubSub;

public abstract class HornetSubscriber extends JedisPubSub
{
    public abstract void onMessage(String event, JSONObject message);

    public void onMessage(String channel, String message)
    {
        String event = channel.replaceFirst("hornet:events:", "");
        this.onMessage(event, new JSONObject(message));
    }

    public void onPMessage(String pattern, String channel, String message)
    {
    }

    public void onPSubscribe(String pattern, int subscribedChannels)
    {
    }

    public void onPUnsubscribe(String pattern, int subscribedChannels)
    {
    }

    public void onSubscribe(String channel, int subscribedChannels)
    {
    }

    public void onUnsubscribe(String channel, int subscribedChannels)
    {
    }
}
