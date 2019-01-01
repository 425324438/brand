package com.cn.brand.enums;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 15:56
 * @Description:
 */
public enum BrandTypeEnum {
    /**
     * 牌 类型
     */
    BLACK("black", "黑"),
    RED("red", "红"),
    PLUM("plum", "梅"),
    SQUARE("square", "方"),
    ;

    BrandTypeEnum(String val, String code) {
        this.val = val;
        this.code = code;
    }


    private String  val;

    private String code;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
