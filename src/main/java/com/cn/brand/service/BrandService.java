package com.cn.brand.service;

import com.cn.brand.model.Brand;
import com.cn.brand.model.Room;

import java.io.IOException;
import java.util.List;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 16:00
 * @Description:
 */
public interface BrandService {




    /**
     * 发牌
     * @param room
     */
    void licensing(Room room,String userId) throws IOException;

    /**
     * 出牌顺序
     * 发完牌之后调用一次，返回给前端确定哪个用户最先操作【抢地主】按钮
     * 抢地主完成之后，地主先出牌，每次用户出牌之后由此函数决定该哪位用户出牌
     * @param room 房间对象
     */
    void roomSequence(Room room);

}
