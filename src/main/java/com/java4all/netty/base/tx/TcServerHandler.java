package com.java4all.netty.base.tx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4all.netty.constant.TransactionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class TcServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcServerHandler.class);
    private static ConcurrentHashMap<String,List<TxSession>> transactions = new ConcurrentHashMap();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
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

        if(transactions.containsKey(groupId)){
            transactions.get(groupId).add(txSession);
        }else {
            List<TxSession> txSessions = new ArrayList<>();
            txSessions.add(txSession);
            transactions.put(groupId,txSessions);
        }

        //接收不同的事务组，每个事务组内单独判断

        switch (command){
            case TransactionType.COMMIT:
                LOGGER.info("【server】groupId={},xid={},resourceId={}，执行{}操作",xid,resourceId,TransactionType.COMMIT);
                break;
            case TransactionType.ROLLBACK:
                //TODO 需要通知这个事务组的所有事务回滚
                LOGGER.info("【server】groupId={},xid={},resourceId={}，执行{}操作",xid,resourceId,TransactionType.ROLLBACK);
                break;
            default:
                LOGGER.info("【server】groupId={},xid={},resourceId={}，执行{}操作",xid,resourceId,TransactionType.REGIST);
                break;
        }

        txSession.setResponse(command+"ed");
        ObjectMapper mapperRe = new ObjectMapper();
        String msgRe = mapperRe.writeValueAsString(txSession);
        ctx.writeAndFlush(Unpooled.copiedBuffer(msgRe.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

