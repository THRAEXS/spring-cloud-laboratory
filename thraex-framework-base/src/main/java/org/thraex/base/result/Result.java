package org.thraex.base.result;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author 鬼王
 * @date 2019/11/29 18:42
 */
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setData(data);

        return r;
    }

    public static Result ok(Object data, Integer code) {
        Result r = new Result();
        r.setCode(Optional.ofNullable(code).orElse(200));
        r.setData(data);

        return r;
    }

    public static Result fail(String message) {
        Result r = new Result();
        r.setCode(-1);
        r.setMessage(message);

        return r;
    }

    public static Result fail(String message, Integer code) {
        Result r = new Result();
        r.setCode(Optional.ofNullable(code).orElse(-1));
        r.setMessage(message);

        return r;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
