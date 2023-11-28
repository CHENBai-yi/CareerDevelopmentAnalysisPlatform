package com.example.config.secretBody;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.secretBody
 * Date:2023/11/27 23:14
 * EMAIL:
 *
 * @author BaiYiChen
 */
// @RestControllerAdvice
public class SecretResponseBodyAdvice extends JSONPResponseBodyAdvice {
    /**
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return
     */
    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter returnType, final MediaType selectedContentType, final Class<? extends HttpMessageConverter<?>> selectedConverterType, final ServerHttpRequest request, final ServerHttpResponse response) {
        return super.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
    }

    /**
     * @param jsonpObject
     * @param contentType
     * @param returnType
     * @param request
     * @param response
     */
    @Override
    public void beforeBodyWriteInternal(final JSONPObject jsonpObject, final MediaType contentType, final MethodParameter returnType, final ServerHttpRequest request, final ServerHttpResponse response) {
        super.beforeBodyWriteInternal(jsonpObject, contentType, returnType, request, response);
    }

    /**
     * @param contentType the content type selected through content negotiation
     * @param request     the current request
     * @param response    the current response
     * @return
     */
    @Override
    protected MediaType getContentType(final MediaType contentType, final ServerHttpRequest request, final ServerHttpResponse response) {
        return super.getContentType(contentType, request, response);
    }

    /**
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(final MethodParameter returnType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
