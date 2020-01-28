package com.java4all.netty.base.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author IT云清
 */
public class EchoClientHandler2 extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelHandlerContext read = ctx.read();
        String s = read.toString();
        System.out.println("======="+s);
    }
}
