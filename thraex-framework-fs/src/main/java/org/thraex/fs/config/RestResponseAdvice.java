package org.thraex.fs.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.thraex.base.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author 鬼王
 * @date 2019/12/09 15:41
 */
@RestControllerAdvice
public class RestResponseAdvice implements ResponseBodyAdvice {

    private Map<Class, Function> proc = new HashMap<>(2);

    public RestResponseAdvice() {
        proc.put(String.class, b -> JSON.toJSON(Result.ok(b)).toString());
        proc.put(Result.class, Function.identity());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        return Optional.ofNullable(proc.get(body.getClass()))
                .map(it -> it.apply(body))
                .orElse(Result.ok(body));
    }

}
