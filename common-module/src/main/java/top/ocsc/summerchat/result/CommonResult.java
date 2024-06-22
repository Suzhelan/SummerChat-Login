package top.ocsc.summerchat.result;


import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonResult extends LinkedHashMap<String, Object> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_BAD_REQUEST = 400;

    public CommonResult() {
    }

    public CommonResult(int code, String msg, Object data) {
        this.setCode(code);
        this.setAction(0);
        this.setMsg(msg);
        this.setData(data);
    }

    public CommonResult(Map<String, ?> map) {
        this.setMap(map);
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }

    public String getMsg() {
        return (String) this.get("msg");
    }

    public Object getData() {
        return this.get("data");
    }

    public CommonResult setAction(int code) {
        this.put("action", code);
        return this;
    }

    public CommonResult setCode(int code) {
        this.put("code", code);
        return this;
    }

    public CommonResult setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public CommonResult setData(Object data) {
        this.put("data", data);
        return this;
    }

    public CommonResult set(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public <T> T get(String key, T defaultValue) {
        Object value = this.get(key);
        if (value == null) {
            return defaultValue;
        }
        return (T) value;
    }


    public CommonResult setMap(Map<String, ?> map) {
        for (String key : map.keySet()) {
            this.put(key, map.get(key));
        }
        return this;
    }

    public static CommonResult ok() {
        return new CommonResult(200, "ok", null);
    }

    public static CommonResult ok(String msg) {
        return new CommonResult(200, msg, null);
    }

    public static CommonResult code(int code) {
        return new CommonResult(code, null, null);
    }

    public static CommonResult data(Object data) {
        return new CommonResult(200, "ok", data);
    }

    public static CommonResult error() {
        return new CommonResult(500, "error", null);
    }

    public static CommonResult error(String msg) {
        return new CommonResult(500, msg, null);
    }

    public static CommonResult get(int code, String msg, Object data) {
        return new CommonResult(code, msg, data);
    }

    public String toString() {
        return "{\"code\": " + this.getCode() + ", \"msg\": " + this.transValue(this.getMsg()) + ", \"data\": " + this.transValue(this.getData()) + "}";
    }

    private String transValue(Object value) {
        if (value == null) {
            return null;
        } else {
            return value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
        }
    }
}
