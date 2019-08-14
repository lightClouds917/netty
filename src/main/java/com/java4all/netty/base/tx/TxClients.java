package com.java4all.netty.base.tx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4all.netty.base.client.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author IT云清
 */
public class TxClients {
    public static void main(String[]args) throws JsonProcessingException {
        test();
    }

    public static void test() throws JsonProcessingException {
        for(int i = 0;i <= 1;i++){
            Map<String,Object> map  = new HashMap<>(8);
            map.put("xid","xid"+i);
            if(i == 1 || i == 3 || i == 5){
                map.put("command","commit");
            }else {
                map.put("command","rollback");
            }
            map.put("resourceId","re"+i);
            ObjectMapper mapper = new ObjectMapper();
            String msg = mapper.writeValueAsString(map);
            Channel channel = getChannel();
            channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        }

    }

    public static Channel getChannel(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress("localhost",2222)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new EchoClientHandler());
                    }
                });
        try {
            Channel channel = bootstrap.connect().sync().channel();
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
