package com.cn.brand;

import com.alibaba.fastjson.JSONObject;
import com.cn.brand.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BrandApplicationTests {

    @Test
    public void contextLoads() {

        User user = new User();
        user.setId("123");
        user.setMaster(true);
        user.setUserName("23wedwed");


        log.info(JSONObject.toJSONString(user));
    }

}

