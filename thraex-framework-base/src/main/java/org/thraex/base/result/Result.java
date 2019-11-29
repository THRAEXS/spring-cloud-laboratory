package org.thraex.base.result;

/**
 * @author 鬼王
 * @date 2019/11/29 18:42
 */
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result ok() {
        return new Result();
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
