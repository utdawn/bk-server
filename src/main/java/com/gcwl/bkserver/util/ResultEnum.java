package com.gcwl.bkserver.util;

public enum ResultEnum {
    /**
     * SUCCESS: 成功
     * ERROR：  错误
     */
    SUCCESS("0000", "成功"),
    ERROR("9999", "失败");

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
