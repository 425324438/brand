package com.cn.brand.model;

import lombok.Data;

/**
 * 接口请求返回类
 * @author 425324438@qq.com
 * @date ${DATE} ${TIME}
 */
@Data
public class ApiResult {

    /**
     * 0:请求成功 1：请求失败
     */
	private Integer status;
    /**
     * 请求信息
     */
	private String msg;
    /**
     * 返回参数
     */
	private Object data;
	
	private static Integer RC_SUCCESS=0;
	private static Integer RC_ERROR=1;
	private static String SUCCESS="success";

	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getdata() {
		return data;
	}
	public void setdata(Object data) {
		this.data = data;
	}
	
	public static ApiResult success(){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_SUCCESS);
		api.setMsg(ApiResult.SUCCESS);
		return api;
	}
	
	public static ApiResult success(String msg){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_SUCCESS);
		api.setMsg(msg);
		return api;
	}
	
	public static ApiResult success(String msg,Object parameter){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_SUCCESS);
		api.setMsg(msg);
		api.setdata(parameter);
		return api;
	}
	
	public static ApiResult error(String msg,Object parameter){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_ERROR);
		api.setMsg(msg);
		api.setdata(parameter);
		return api;
	}
	
	public static ApiResult error(String msg){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_ERROR);
		if(null == msg){
			api.setMsg("无结果返回");
		}else{
			api.setMsg(msg);
		}
		return api;
	}
	public static ApiResult error(){
		ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_ERROR);
		api.setMsg("无结果返回");
		return api;
	}

    public static ApiResult error(Integer status,String msg){
        ApiResult api = new ApiResult();
		api.setStatus(ApiResult.RC_ERROR);
        api.setMsg(msg);
        return api;
    }

}
