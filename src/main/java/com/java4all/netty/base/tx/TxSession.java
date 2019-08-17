package com.java4all.netty.base.tx;

import lombok.Data;

/**
 * @author IT云清
 * Just a small demo ,do not nedd to distinguish between global and local transations
 */
@Data
public class TxSession {

    /**全局事务id*/
    private String xid;
    /**本地事务id*/
    private String localTransactionId;
    /**事务组*/
    private String groupId;
    /**资源id*/
    private String resourceId;
    /**命令*/
    private String command;
    /**TC返回信息*/
    private String response;

}
