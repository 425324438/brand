package com.cn.brand.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author: 425324438@qq.com
 * @Date: 2020/2/8 19:57
 * @Description: socket请求数据包
 */
@Data
public class SocketDataPackage {

    private String msgType;

    private Object request;

    private Object response;

}
