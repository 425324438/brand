package com.cn.brand.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.cn.brand.socket.SockerServer;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 16:33
 * @Description:
 */
@Data
@Getter
public class User {

    private String id;

    private String userName;

    private boolean master;

    private boolean licensing;

    @JSONField(serialize = false, deserialize = false)
    private SockerServer sockerServer;

    /**
     * 用户手中的 牌
     */
    private List<Brand> brands;
}
