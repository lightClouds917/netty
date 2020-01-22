package com.java4all.netty.base.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author IT云清
 */
public class EchoServer implements InitializingBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);
    private final int port = 8888;

    public void start(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addFirst(new EchoServerHandler1())
                                .addLast(new EchoServerHandler2())
                                .addLast(new EchoServerHandler3())
                                .addLast(new EchoServerHandlerA())
                                .addLast(new EchoServerHandlerB())
                                .addLast(new EchoServerHandlerC());
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind().sync();
            LOGGER.info("【momo server】started at port:{}",port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[]args){
//        new EchoServer().start();
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new EchoServer().start();
    }
}
