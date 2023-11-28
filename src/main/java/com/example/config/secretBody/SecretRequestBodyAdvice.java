package com.example.config.secretBody;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.secretBody
 * Date:2023/11/27 23:12
 * EMAIL:
 *
 * @author BaiYiChen
 */
// @RestControllerAdvice
public class SecretRequestBodyAdvice extends RequestBodyAdviceAdapter {
    /**
     * @param methodParameter the method parameter
     * @param targetType      the target type, not necessarily the same as the method
     *                        parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType   the selected converter type
     * @return
     */
    @Override
    public boolean supports(final MethodParameter methodParameter, final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }
}
