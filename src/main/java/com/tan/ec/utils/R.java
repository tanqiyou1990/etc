
package com.tan.ec.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 响应信息主体
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    private String msg = "success";

    private int code = SUCCESS;

    private boolean success = true;

    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        if(!StringUtils.isEmpty(msg)){
            this.msg = msg;
        }
    }

    public R(T data,boolean success, String msg) {
        super();
        this.data = data;
        if(!StringUtils.isEmpty(msg)){
            this.msg = msg;
        }
        this.code = success ? SUCCESS : FAIL;
        this.success = success;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
        this.success = false;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {  return success; }

    public void setSuccess(boolean success) { this.success = success;  }
}
