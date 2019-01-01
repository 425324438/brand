package com.cn.brand.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.brand.model.Room;
import com.cn.brand.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 19:20
 * @Description: 房间
 */
public interface RoomService {


    /**
     * 创建房间
     * @return
     */
    Room createRoom(User user, String roomName);

    /**
     * 获取所有房间
     * @return
     */
    JSONArray roomList();

    /**
     * 加入房间
     * @param room
     * @param user
     * @return
     */
    boolean addRoom(Room room, User user);

    /**
     * 用户退出房间
     * @param room
     * @param user
     * @return
     */
    boolean outRoom(Room room, User user);

    /**
     * 抢地主
     * @param roomId
     * @param userId
     * @param multiple
     */
    void robLandlord(String roomId, String userId, Integer multiple);

    /**
     * 房间内 socket 通知
     * @param type 通知类型，前端socket 接收后 判断用
     * @param msg 通知内容 service 不做处理 直接发到客户端
     */
    void sendRoomMessage(Room room, String type, JSONObject msg);

}
