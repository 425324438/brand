package com.cn.brand.model;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 15:49
 * @Description: 一张牌
 */

public class Brand {

    private Integer id;

    private String type;

    private Integer val;

    public Brand() {
    }

    public Brand(Integer id, String type, Integer val) {
        this.id = id;
        this.type = type;
        this.val = val;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Brand{" + "id=" + id + ", type='" + type + '\'' + ", val=" + val + '}';
    }
}
