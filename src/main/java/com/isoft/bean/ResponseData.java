package com.isoft.bean;

import java.io.Serializable;

/**
 * Author by Rounding
 * Date : 2020.7.8
 * 服务器端返回客户端数据统一形式
 */
public class ResponseData implements Serializable {
    /**
     * 返回结果码
     */
    private int errCode ;
    /**
     * 结果信息描述
     */
    private String msg ;
    /**
     * 返回具体结果数据
     */
    private Object data ;
    public ResponseData(){}

    public ResponseData(int errCode, String msg, Object data) {
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "errCode=" + errCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
