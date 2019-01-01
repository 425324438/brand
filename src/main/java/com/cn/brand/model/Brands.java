package com.cn.brand.model;

import com.cn.brand.enums.BrandTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ylshi@ronglian.com
 * @Date: 2018/12/30 17:15
 * @Description:
 */
public class Brands {

    private List<Brand> brands;

    public static Integer length;

    public Brands() {
        brands = createBrand(54);
    }

    public static List<Brand> createBrand(Integer length){
        List<Brand> brands = new ArrayList<>();
        String[] typeList = {BrandTypeEnum.BLACK.getCode(), BrandTypeEnum.RED.getCode(),
                BrandTypeEnum.PLUM.getCode(), BrandTypeEnum.SQUARE.getCode()};
        int val = 1;

        for(int i =1 ;i <= length;){
            for(int x = 0; x <= 3; x++){
                Brand brand = new Brand(i, typeList[x] , val);
                brands.add(brand);
                i++;
                if(i >= 54 ){
                    break;
                }
            }
            val ++ ;
        }
        Brands.length = length;
        return brands;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
