package com.cn.brand.controller;

import com.cn.brand.chche.RoomChche;
import com.cn.brand.model.ApiResult;
import com.cn.brand.model.Room;
import com.cn.brand.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 19:16
 * @Description:
 */
@RestController
@RequestMapping("brand")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 发牌
     * @param roomId 房间
     *               从前有个地方的漕运很乱，新上任的河道总督姓赶，他规定要做漕运必须到县里拿一张通行证，就是一个精致的小木牌。
     *      这天很多漕帮的人一起去领通行证，有人问他们去干嘛，他们叹气道：“姓赶河官 在县发牌。”那人一听，说：“哦，聚众赌博啊。
     * @return
     */
    @PostMapping("licensing/{roomId}/{userId}")
    public ApiResult licensing(@PathVariable("roomId") String roomId ,@PathVariable("userId") String userId){
        ApiResult result = ApiResult.error();
        try{
            Room room = RoomChche.roomMap.get(roomId);
            brandService.licensing(room, userId);
            result = ApiResult.success();
        }catch (Exception e){
            log.error(e.toString() ,e);
            result.setMsg(e.getMessage());
        }
        return  result;
    }


}
