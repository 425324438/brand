package com.cn.brand.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 16:32
 * @Description: 房间包括 整副牌，包含 User
 */
@Data
public class Room {

    private String id;

    private Brands brands;

    private String name;

    private User masterUser;

    private List<User> users;

    /**
     * 保留三张底牌
     */
    private List<Brand> bottomBrand;

    public Room(){}

    public Room(String id, Brands brands, List<User> users, String roomName) {
        this.id = id;
        this.brands = brands;
        this.users = users;
        this.name = roomName;
    }


}
