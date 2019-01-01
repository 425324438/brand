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


}
