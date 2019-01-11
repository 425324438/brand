package com.cn.brand;

import com.cn.brand.model.ApiResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 暮色听雨声
 * @date 2019/1/11
 */
@SpringBootApplication
@RestController
public class BrandApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrandApplication.class, args);
    }


    @RequestMapping("/")
    public ApiResult index(){
        return ApiResult.success("启动成功");
    }
}

