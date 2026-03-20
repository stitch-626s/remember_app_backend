package com.remember_app.remember.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "500";

    private String code;
    private String message;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMessage("请求成功");
        return result;
    }

    public static Result success(Object data){
        Result result  = success();
        result.setData(data);
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result error(String code, String message){
        Result result = error(message);
        result.setCode(code);
        return result;
    }


}
