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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IT云清
 */
public class TcServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcServerHandler.class);
    private static ConcurrentHashMap<String,Set<TxSession>> transactions = new ConcurrentHashMap();

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

        if(transactions.containsKey(xid)){
            transactions.get(xid).add(txSession);
        }else {
            //We need to think about concurrency，But now TC is single ,so do not need distribute lock
            synchronized (this){
                Set<TxSession> txSessions = new HashSet<>();
                txSessions.add(txSession);
                transactions.put(groupId,txSessions);
            }
        }

        //接收不同的事务组，每个事务组内单独判断
        //TODO 考虑阻塞
        //如果有rollback的请求，要回滚xid相同的事务，事务组作用？

        switch (command){
            case TransactionType.COMMIT:
                LOGGER.info("【server】groupId={},xid={},resourceId={}，执行{}操作",xid,resourceId,TransactionType.COMMIT);
                break;
            case TransactionType.ROLLBACK:
                //TODO 需要通知这个xid的所有事务回滚
                Set<TxSession> txSessionsNeedRollback = transactions.get(xid);
                txSessionsNeedRollback.stream().forEach(session -> {
                    //向每一个分支事务发送回滚指令
                });

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

