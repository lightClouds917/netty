package com.java4all.netty.base.client;

import com.java4all.netty.base.server.EchoServerHandler2;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class EchoClientHandler2 extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoClientHandler2.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelHandlerContext read = ctx.read();
        String s = read.toString();
        System.out.println("======="+s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoClientHandler2 has channelActive");

    }
}
