package com.cn.brand.Util;

import com.cn.brand.model.Brand;

import java.util.List;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2019/1/1 14:19
 * @Description:
 */
public class BreanUtils {

    /**
     * 洗牌算法
     * @param array
     * @return
     */
    public static List<Brand> shuffle(List<Brand> array) {
        int m = array.size(), i;
        boolean next = true;
        while (next) {
            i = (int) Math.floor(Math.random() * m--);
            Brand t = array.get(m);
            array.set(m, array.get(i));
            array.set(i, t);
            if(m <= 0){
                next = false;
            }
        }
        return array;
    }
}
