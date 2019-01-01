package com.cn.brand.chche;

import com.cn.brand.model.Room;
import com.cn.brand.model.User;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/31 13:37
 * @Description:
 */
public class RoomChche {

    /**
     * 房间列表，key ：房间ID
     */
    public static Map<String, Room> roomMap = new ConcurrentHashMap<>();

    /**
     * 拥有房间的用户
     */
    public static Map<String, User> userRoom = new Hashtable<>();

}
