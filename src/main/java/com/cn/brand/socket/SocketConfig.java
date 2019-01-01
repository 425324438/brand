package com.cn.brand.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 21:03
 * @Description:
 */
@Configuration
public class SocketConfig {
    /**
     * tomcat 内无需此配置
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
