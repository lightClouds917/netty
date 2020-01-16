package com.java4all.netty.base.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4all.netty.constant.TransactionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class EchoServerHandler1 extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServerHandler1.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelActive");
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("EchoServerHandler1 has channelRead");
        ByteBuf in = (ByteBuf) msg;
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(in.toString(CharsetUtil.UTF_8), Map.class);
        String command = map.get("command").toString();
        String xid = map.get("xid").toString();
        String resourceId = map.get("resourceId").toString();

        switch (command){
            case TransactionType.COMMIT:
                LOGGER.info("【server】xid为{}，resourceId为{}，执行{}操作",xid,resourceId,TransactionType.COMMIT);
                break;
            case TransactionType.ROLLBACK:
                LOGGER.info("【server】xid为{}，resourceId为{}，执行{}操作",xid,resourceId,TransactionType.ROLLBACK);
                break;
            default:
                LOGGER.info("【server】xid为{}，resourceId为{}，执行{}操作",xid,resourceId,TransactionType.REGIST);
                break;
        }

        String response = command+"ed";
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

