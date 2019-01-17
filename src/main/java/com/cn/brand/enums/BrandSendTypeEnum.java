package com.cn.brand.enums;


/**
 * @author: ylshi@ronglian.com
 * @Date: 2019/1/17 22:51
 * @Description:
 */
public enum BrandSendTypeEnum {
    /**
     *   单张
     *   对子 最小2张
     *   连对 2的倍数
     *   三张
     *   三代一 最小4张
     *   三代二 最小5张
     *   顺子，最小5张
     *   飞机
     *   炸弹 最小4张
     *   王炸
     */
    SINGLE("single","单", 1),
    TWIN("twin", "对子", 2),
    LINK_TWIN("linkTwin", "连对", 2),
//    TWIN("twin", "三张", 2),
//    TWIN("twin", "三代一", 2),
//    TWIN("twin", "三代二", 2),
    straight("straight", "顺子", 2),
    aircraft("aircraft", "飞机", 2),
    Bomb("Bomb", "炸弹", 4),
    kingBomb("kingBomb", "王炸", 2),
    ;

    private String type;

    private String msg;

    private Integer minSize;

    BrandSendTypeEnum(String single, String msg, Integer minSize) {
        this.type = type;
        this.msg = msg;
        this.minSize = minSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
