package com.java4all.netty.base.tx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4all.netty.base.server.EchoServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class TxClientHandler extends ChannelInboundHandlerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(TxClientHandler.class);

    public TxClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        LOGGER.info("完成");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        ObjectMapper mapper = new ObjectMapper();
        TxSession txSession = mapper.readValue(in.toString(CharsetUtil.UTF_8), TxSession.class);
        String command = txSession.getCommand();
        String xid = txSession.getXid();
        String resourceId = txSession.getResourceId();
        String groupId = txSession.getGroupId();
        String response = txSession.getResponse();

        LOGGER.info("reveive message:groupId={},xid={},resourceId={},has {}",groupId,xid,resourceId,response);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
