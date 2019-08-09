package com.java4all.netty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4all.netty.constant.TransactionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Map<String,Object> map  = new HashMap<>(8);
        map.put("xid","xid10000");
        map.put("command","commit");
        map.put("resourceId","re15");
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(map);
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String response = in.toString(CharsetUtil.UTF_8);
        if(TransactionType.COMMITED.equals(response)){
            LOGGER.info("【client】Transaction has commited");
        }else {
            LOGGER.info("【client】Faild to commit transaction");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
