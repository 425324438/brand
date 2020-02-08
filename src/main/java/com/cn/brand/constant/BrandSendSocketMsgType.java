package com.cn.brand.constant;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2019/1/1 14:29
 * @Description: socket 通信消息类型
 */
public class BrandSendSocketMsgType {

    /******** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /******** ***** ***** ***** 房间主要操作 ***** ***** ***** ***** ***** */
    /******** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /**
     * 开始发牌
     */
    public static final String LICENSING_Action = "licensingAction";

    /**
     * 用户同意发牌
     */
    public static final String LICENSING = "licensing";

    /**
     * 抢地主
     */
    public static final String LANDLORD = "landlord";

    /**
     * 用户发牌
     */
    public static final String SEND_BRAND_MSG = "sendBrandMsg";

    /**
     * 房间操作顺序
     */
    public static final String ROOM_SEQUENCE = "roomSequence";



    /******** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /******** ***** ***** ***** 聊天窗口 ***** ***** ***** ***** ***** */
    /******** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    public static final String ROOM_USER_MSG = "room_user_msg";

}
