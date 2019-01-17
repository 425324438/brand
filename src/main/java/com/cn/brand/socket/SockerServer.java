package com.cn.brand.socket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.brand.Util.SpringUtil;
import com.cn.brand.chche.RoomChche;
import com.cn.brand.constant.BrandSendSocketMsgType;
import com.cn.brand.constant.RoomSendSocketMsgType;
import com.cn.brand.model.Brand;
import com.cn.brand.model.Room;
import com.cn.brand.model.User;
import com.cn.brand.service.BrandService;
import com.cn.brand.service.RoomService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 16:35
 * @Description:
 */
@Controller
@ServerEndpoint(value = "/websocket")
public class SockerServer {
    protected static Log logger = LogFactory.getLog(SockerServer.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static volatile int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    public static CopyOnWriteArraySet<SockerServer> webSocketSet = new CopyOnWriteArraySet<SockerServer>();

    /**
     * 存储socket Session，Session ID为键
     */
    public static Map<String, SockerServer> map = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    @Autowired
    private RoomService roomService = (RoomService)SpringUtil.getBean("roomService");
    @Autowired
    private BrandService brandService = (BrandService) SpringUtil.getBean("brandService");
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        map.put(this.session.getId(), this);

        User user = new User();
        user.setSockerServer(this);
        user.setId(session.getId());
        RoomChche.users.put(this.session.getId(), user);

        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        logger.info("有新连接加入！当前在线人数为" + RoomChche.users.size());
        try {

            JSONObject json = new JSONObject();
            json.put("sessionId", session.getId());
            json.put("peopelNum", RoomChche.users.size());
            //返回给用户端 sessionId
            sendMessage(JSONObject.toJSONString(json));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String roomId = jsonObject.getString("roomId");
        String userId = jsonObject.getString("userId");
        Room room = RoomChche.roomMap.get(roomId);

        try {
            String type = jsonObject.getString("type");
            switch (type){
                // 抢地主
                case BrandSendSocketMsgType.LANDLORD:
                    Integer multiple = jsonObject.getInteger("multiple");
                    JSONObject msg = new JSONObject();
                    msg.put("userId", userId);
                    msg.put("multiple", multiple);
                    roomService.sendRoomMessage(room, RoomSendSocketMsgType.ROOM_MSG ,msg);
                    roomService.robLandlord(roomId, userId, multiple);
                    break;
                // 用户发牌
                case BrandSendSocketMsgType.SEND_BRAND_MSG:
                    List<Brand> brand = JSONArray.parseArray(jsonObject.getString("Brand"), Brand.class);
                    boolean compare = brandService.compareUserSendBrand(room, userId, brand);

                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SockerServer sockerServer = map.get(session.getId());
            if(sockerServer.session == session){
                sockerServer.sendMessage("请求失败："+ e.toString());
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        String id = this.session.getId();
        //在线数减1
        subOnlineCount();
        RoomChche.users.remove(id);
        map.remove(id);
        logger.info("有一连接关闭！当前在线人数为" + RoomChche.users.size());
    }


    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        logger.error(error.toString(), error);
        RoomChche.users.remove(session.getId());
        map.remove(session.getId());
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) {
        webSocketSet.forEach(item -> {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                logger.error(e.toString(),e);
            }
        });
    }

    /**
     * 发送信息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message){
        try {
            if (this.session.isOpen()){
                this.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }




    public Session getSession() {
        return this.session;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        SockerServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        SockerServer.onlineCount--;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        SockerServer that = (SockerServer) o;
        return Objects.equals(session, that.session) && Objects.equals(roomService, that.roomService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, roomService);
    }
}
