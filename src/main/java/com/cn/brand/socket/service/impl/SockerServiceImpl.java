package com.cn.brand.socket.service.impl;

import com.cn.brand.socket.SockerServer;
import com.cn.brand.socket.service.SockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/31 14:00
 * @Description:
 */
@Service
@Slf4j
public class SockerServiceImpl implements SockerService {


    @Override
    public void sendAllMessage(String message) {
        SockerServer.webSocketSet.forEach(item -> {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                log.error(e.toString(),e);
            }
        });
    }
}
