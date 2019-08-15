package com.java4all.netty.base.tx;

import lombok.Data;

/**
 * @author wangzhongxiang
 * @date 2019年08月15日 20:23:37
 */
@Data
public class TxSession {

    /**事务id*/
    private String xid;
    /**事务组*/
    private String groupId;
    /**资源id*/
    private String resourceId;
    /**命令*/
    private String command;
    /**TC返回信息*/
    private String response;

}
