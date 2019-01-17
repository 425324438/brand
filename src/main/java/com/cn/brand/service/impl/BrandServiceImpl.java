package com.cn.brand.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.brand.Util.BrandUtils;
import com.cn.brand.chche.RoomChche;
import com.cn.brand.constant.BrandSendSocketMsgType;
import com.cn.brand.model.Brand;
import com.cn.brand.model.Room;
import com.cn.brand.model.User;
import com.cn.brand.service.BrandService;
import com.cn.brand.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 16:06
 * @Description:
 */
@Slf4j
@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private RoomService roomService;

    /**
     * 发牌
     * @param room
     * @param userId
     * @throws IOException
     */
    @Override
    public void licensing(Room room,String userId) throws IOException {
        userLicensing(room, userId);
        boolean action = action(room);
        log.info("开始发牌：" + action);
        if(action){
            List<User> users = room.getUsers();
            List<Brand> brand = room.getBrands().getBrands();

            List<Brand> shuffle = BrandUtils.shuffle(brand);
            for(int i = shuffle.size()-1 ; i >= 0;){
                if(i <= 3){
                    if(CollectionUtils.isEmpty(room.getBottomBrand())){
                        room.setBottomBrand(new ArrayList<>());
                    }
                    room.getBottomBrand().add(shuffle.get(i));
                    i--;
                    continue;
                }
                for(User user : users){
                    if(CollectionUtils.isEmpty(user.getBrands())){
                        user.setBrands(new ArrayList<>());
                    }
                    user.getBrands().add(shuffle.get(i));
                    i--;
                }
            }

            users.forEach(user -> {
                user.setLicensing(false);
                List<Brand> brands = user.getBrands();
                JSONObject json  = new JSONObject();
                json.put("type",BrandSendSocketMsgType.LICENSING_Action);
                json.put("brands", brands);
                user.getSockerServer().sendMessage(JSONObject.toJSONString(json));
            });
            roomSequence(room);
        }
    }

    /**
     * 操作顺序
     * 第一个操作的人是房间主人
     * @param room
     */
    @Override
    public void roomSequence(Room room) {
        JSONObject json = new JSONObject();

        if(room.getCurrentUser() == null){
            room.setCurrentUser(room.getMasterUser());
        } else {
            Iterator<User> iterator = room.getUsers().iterator();
            User onuser = null;
            while (iterator.hasNext()){
                User user = iterator.next();
                if(onuser == null){
                    onuser = user;
                }
                if(room.getCurrentUser().getId().equals(user.getId())){
                    if(iterator.hasNext()){
                        room.setCurrentUser(iterator.next());
                    } else {
                        room.setCurrentUser(onuser);
                    }
                }
            }
        }
        json.put("sequenceUserId", room.getCurrentUser().getId());
        roomService.sendRoomMessage(room, BrandSendSocketMsgType.ROOM_SEQUENCE, json);
    }

    /**
     * 比较用户发的牌
     * @return
     */
    @Override
    public boolean compareUserSendBrand(Room room, String userId, List<Brand> brands){
        //没有出牌直接操作下一个出牌人
        if(CollectionUtils.isEmpty(brands)){
            this.roomSequence(room);
            return true;
        }
        User user = RoomChche.users.get(userId);
        //第一个出牌的用户直接返回 true
        if (CollectionUtils.isEmpty(room.getCurrentUser().getCurrentBrands())){
            user.setCurrentBrands(brands);
            room.setCurrentUser(user);
            return true;
        }
        return compareBrands(room, user, brands);
    }

    public boolean compareBrands(Room room, User user, List<Brand> brands){
        //先对比是不是同一个用户发来的牌。如果是，直接返回 true
        String currentUserId = room.getCurrentUser().getId();
        if(StringUtils.equals(currentUserId, user.getId())){
            return true;
        }
        //两个不同的用户对比出牌的大小
        List<Brand> roomCurrentBrands = room.getCurrentUser().getCurrentBrands();

        return true;
    }

    /**
     * 用户同意发牌
     */
    private void userLicensing(Room room,String userId){
        List<User> users = room.getUsers();
        JSONObject json  = new JSONObject();
        users.forEach(user -> {
            if(user.getId().equals(userId)){
                user.setLicensing(true);
                json.put("userId",userId);
            }
        });
        roomService.sendRoomMessage(room,BrandSendSocketMsgType.LICENSING,json);
        room.setUsers(users);
    }

    /**
     * 校验是否可以发牌
     * @param room
     * @return
     */
    private boolean action(Room room ){
        List<User> users = room.getUsers();
        Set<Boolean> set = new HashSet<>();

        for (User user : users) {
            set.add(user.isLicensing());
        }

        return !set.contains(false);
    }
}
