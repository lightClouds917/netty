package com.java4all.netty.server;

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
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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

