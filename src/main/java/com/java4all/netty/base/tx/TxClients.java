package com.java4all.netty.base.tx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author IT云清
 */
public class TxClients {

    private static final Integer PARTICIPANT_COUNT = 10;

    public static void main(String[]args) throws JsonProcessingException {
        test();
    }

    /**
     * 模拟参与者节点 向 TC汇报消息
     * @throws JsonProcessingException
     */
    public static void test() throws JsonProcessingException {
        for(int i = 0;i <= PARTICIPANT_COUNT;i++){
            TxSession txSession = new TxSession();
            txSession.setXid("xid"+i);
            txSession.setCommand("re"+i);
            txSession.setGroupId("txOne");
            if(i == 1 || i == 3 || i == 5 || i == 7){
                txSession.setCommand("commit");
            }else {
                txSession.setCommand("rollback");
            }

            ObjectMapper mapper = new ObjectMapper();
            String msg = mapper.writeValueAsString(txSession);
            Channel channel = getChannel();
            channel.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
            try {
                Thread.sleep(1000);
                System.out.println("睡了1s");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                                .addLast(new TxClientHandler());
                    }
                });
        try {
            Channel channel = bootstrap.connect().sync().channel();
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
