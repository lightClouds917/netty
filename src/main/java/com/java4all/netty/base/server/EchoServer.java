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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author IT云清
 */
@Component
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
                                .addFirst("handler1",new EchoServerHandler1())
                                .addAfter("handler1","handler2",new EchoServerHandler2())
                                .addAfter("handler1","handler3",new EchoServerHandler3())
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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }



    public void test(){
        System.out.println("可用处理器数量："+Runtime.getRuntime().availableProcessors());
    }

    //https://www.cnblogs.com/feiyun126/p/7685312.html
}
