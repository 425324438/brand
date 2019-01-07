package com.cn.brand.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.brand.chche.RoomChche;
import com.cn.brand.model.ApiResult;
import com.cn.brand.model.Room;
import com.cn.brand.model.User;
import com.cn.brand.service.RoomService;
import com.cn.brand.socket.SockerServer;
import com.cn.brand.socket.service.SockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 20:35
 * @Description:
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private SockerService sockerService;

    /**
     * 创建房间
     * @param userId
     * @return 返回房间ID
     */
    @PostMapping("createRoom/{userId}")
    public ApiResult createRoom(@PathVariable("userId") String userId, String roomName){
        ApiResult result = ApiResult.error();
        try {
            User user = SockerServer.users.get(userId);

            Room room = roomService.createRoom(user, roomName + (int)(Math.floor(Math.random() * 10000) + 10000));
            RoomChche.roomMap.put(room.getId(), room);

            result = ApiResult.success();
            JSONObject json = new JSONObject();
            json.put("roomId" , room.getId());
            json.put("roomName" , room.getName());
            result.setdata(JSONObject.toJSON(json));

            json.put("type","createRoom");
            sockerService.sendAllMessage(JSONObject.toJSONString(json));

        } catch (Exception e){
            log.error(e.toString() ,e);
            result = ApiResult.error(e.getMessage());
        }
        return result;
    }

    /**
     * 获取房间列表
     * @return
     */
    @GetMapping("roomList")
    public ApiResult roomList(){
        ApiResult result = ApiResult.error();
        try {
            result = ApiResult.success();
            JSONArray rooms = roomService.roomList();
            result.setdata(rooms);

        } catch (Exception e){
            log.error(e.toString() ,e);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 加入房间
     * 加入成功后，推送信息给改房间内的其他用户
     * @param roomId 房间ID
     * @param userId 用户Id
     * @return
     */
    @PostMapping("addRoom/{roomId}/{userId}")
    public ApiResult addRoom(@PathVariable("roomId") String roomId,@PathVariable("userId") String userId){
        ApiResult result = ApiResult.error();
        try {
            result = ApiResult.success();
            User user = SockerServer.users.get(userId);
            Room room = RoomChche.roomMap.get(roomId);
            if(room.getUsers().size() > 3){
                result = ApiResult.error("房间人数已满");
            } else {
                boolean addRoom = roomService.addRoom(room, user);
                if(addRoom){
                    result.setMsg("加入房间成功");

                    JSONObject json = new JSONObject();
                    json.put("userId", user.getId());
                    json.put("userName", user.getUserName());
                    json.put("userList",room.getUsers().size());
                    roomService.sendRoomMessage(room,"addRoom",json);
                } else {
                    result.setMsg("加入房间失败");
                }
                result.setdata(addRoom);
            }

        } catch (Exception e){
            log.error(e.toString() ,e);
            result = ApiResult.error(e.getMessage());
        }

        return result;
    }

    /**
     * 用户退出房间
     * @param roomId
     * @param userId
     * @return
     */
    @PostMapping("outRoom/{roomId}/{userId}")
    public ApiResult outRoom(@PathVariable("roomId") String roomId,@PathVariable("userId") String userId){
        ApiResult result = ApiResult.error();

        try {
            User user = SockerServer.users.get(userId);
            Room room = RoomChche.roomMap.get(roomId);

            if(room != null){
                boolean outRoom = roomService.outRoom(room, user);
                if (outRoom){
                    //退出通知
                    JSONObject json = new JSONObject();
                    json.put("userId", user.getId());
                    json.put("userName", user.getUserName());
                    roomService.sendRoomMessage(room,"outRoom",json);
                }
                result.setdata(outRoom);
            }

        } catch (Exception e){
            log.error(e.getMessage(), e);
            result = ApiResult.error(e.getMessage());
        }

        return result;
    }


    /**
     * 房间详细信息
     * @param roomId
     * @return
     */
    @GetMapping("{roomId}/detail")
    public ApiResult roomDetail(@PathVariable("roomId") String roomId){

        ApiResult result = ApiResult.error();

        try {
            result = ApiResult.success();
            Room room = RoomChche.roomMap.get(roomId);
            result.setdata(room);
        } catch (Exception e){
            log.error(e.toString() ,e);
            result.setMsg(e.toString());
        }

        return result;
    }
}
