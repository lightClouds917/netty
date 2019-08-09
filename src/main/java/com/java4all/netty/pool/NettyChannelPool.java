package com.java4all.netty.pool;

import io.netty.channel.Channel;
import java.util.Random;

/**
 * @author IT云清
 */
public class NettyChannelPool {

    private Channel[] channels;
    private Object[] locks;
    private static final int MAX_CHANNEL_COUNT = 4;


    public Channel syncGetChannel(){
        int index = new Random().nextInt(MAX_CHANNEL_COUNT);
        Channel channel = channels[index];
        if(null != channel && channel.isActive()){
            return channel;
        }



        return null;
    }



    public NettyChannelPool(Channel[] channels, Object[] locks) {
        this.channels = channels;
        this.locks = locks;
    }
}
