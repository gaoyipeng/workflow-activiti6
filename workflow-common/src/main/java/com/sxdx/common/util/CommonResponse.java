package com.sxdx.common.util;


import com.sxdx.common.constant.CodeEnum;

import java.util.HashMap;

public class CommonResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public CommonResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public CommonResponse code(String code) {
        this.put("code", code);
        return this;
    }

    public CommonResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public CommonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
