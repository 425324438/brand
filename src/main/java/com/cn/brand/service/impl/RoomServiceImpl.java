package com.cn.brand.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.brand.chche.RoomChche;
import com.cn.brand.constant.BrandSendSocketMsgType;
import com.cn.brand.controller.RoomController;
import com.cn.brand.model.Brand;
import com.cn.brand.model.Brands;
import com.cn.brand.model.Room;
import com.cn.brand.model.User;
import com.cn.brand.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 19:49
 * @Description:
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService {


    @Override
    public Room createRoom(User user, String roomName) {
        user.setMaster(true);
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Brands brands = new Brands();
        Room room = new Room(UUID.randomUUID().toString(),brands, userList, roomName);
        room.setMasterUser(user);

        RoomChche.userRoom.put(user.getSockerServer().getSession().getId(), user);
        return room;
    }

    @Override
    public JSONArray roomList() {
        JSONArray rooms = new JSONArray();

        Set<String> keys = RoomChche.roomMap.keySet();
        if(! CollectionUtils.isEmpty(keys)){
            keys.forEach(key ->{
                Room room = RoomChche.roomMap.get(key);
                JSONObject roomObj = new JSONObject();
                roomObj.put("roomId" , room.getId());
                roomObj.put("roomName" , room.getName());

                rooms.add(roomObj);
            });
        }

        return rooms;
    }

    @Override
    public boolean addRoom(Room room, User user) {
        List<User> users = room.getUsers();
        users.add(user);
        room.setUsers(users);

        RoomChche.roomMap.put(room.getId(), room);
        return true;
    }

    @Override
    public boolean outRoom(Room room, User user) {
        RoomChche.userRoom.remove(user.getId());
        List<User> users = room.getUsers();
        users.remove(user);
        room.setUsers(users);

        RoomChche.roomMap.put(room.getId(), room);

        return false;
    }

    @Override
    public void sendRoomMessage(Room room, String type, JSONObject msg) {
        room.getUsers().forEach(user1 -> {
            JSONObject json = new JSONObject();
            json.put("type", type);
            json.put("roomId", room.getId());
            json.put("msg", msg);
            user1.getSockerServer().sendMessage(JSONObject.toJSON(json).toString());
        });
    }

    @Override
    public void robLandlord(String roomId, String userId, Integer multiple){
        Room room = RoomChche.roomMap.get(roomId);
        JSONObject jsonObject = new JSONObject();

        room.getUsers().forEach(itemUser -> {
            if(itemUser.getId().equals(userId)){
                itemUser.setMultiple(multiple);
            }
        });
        //对比房间内，谁的倍率最大他就是地主
        User user = null;
        Iterator<User> iteratorUser = room.getUsers().iterator();
        while (iteratorUser.hasNext()){
            if(user == null){
                user = iteratorUser.next();
            } else {
                User nextUser = iteratorUser.next();
                if(nextUser.getMultiple() != null){
                    if(user.getMultiple() < nextUser.getMultiple()){
                        user = nextUser;
                        if(! iteratorUser.hasNext()){
                            user.setLandlord(true);
                            List<Brand> bottomBrand = room.getBottomBrand();
                            user.getBrands().addAll(bottomBrand);
                            jsonObject.put("IsLandlordUserId", user.getId());
                            jsonObject.put("bottomBrand", bottomBrand);
                        }
                    }
                }
            }
        }

        sendRoomMessage(room, BrandSendSocketMsgType.LANDLORD,jsonObject);

    }


}
