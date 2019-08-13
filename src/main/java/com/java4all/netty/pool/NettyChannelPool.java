package com.java4all.netty.pool;

import com.java4all.netty.base.client.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.Attribute;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author IT云清
 * 参考：https://www.jianshu.com/p/7132d84c2461
 */
public class NettyChannelPool {

    private Channel[] channels;
    private Object[] locks;
    private static final int MAX_CHANNEL_COUNT = 4;


    public Channel syncGetChannel() throws InterruptedException {
        int index = new Random().nextInt(MAX_CHANNEL_COUNT);
        Channel channel = channels[index];
        if(null != channel && channel.isActive()){
            return channel;
        }
        //TODO why
        synchronized (locks[index]){
            channel = channels[index];
            if(null != channel && channel.isActive()){
                return channel;
            }
            channel = this.connectToServer();
            channels[index] = channel;
        }

        return channel;
    }

    private Channel connectToServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new SelfDefineEncodeHandler())
                                .addLast(new EchoClientHandler());
                        //TODO 待修改
                    }
                });
        ChannelFuture future = bootstrap.connect("localhost", 7777);
        Channel channel = future.sync().channel();
        Attribute<Map<Integer, Object>> attribute = channel.attr(ChannelUtils.DATA_MAP_ATTRIBUTEKEY);
        ConcurrentHashMap<Integer, Object> dataMap = new ConcurrentHashMap<>(8);
        attribute.set(dataMap);
        return channel;
    }



    public NettyChannelPool(Channel[] channels, Object[] locks) {
        this.channels = channels;
        this.locks = locks;
    }
}
