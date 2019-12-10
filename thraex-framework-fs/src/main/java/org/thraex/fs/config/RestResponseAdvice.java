package org.thraex.fs.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.thraex.base.result.Result;

/**
 * @author 鬼王
 * @date 2019/12/09 15:41
 */
@RestControllerAdvice
public class RestResponseAdvice implements ResponseBodyAdvice {

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
        System.out.println(String.class.isInstance(body));
        if (body instanceof Result || body instanceof String) {
            return body;
        }

        return Result.ok(body);
    }

}
