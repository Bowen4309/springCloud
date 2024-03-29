package com.Bibo.common.util;

import com.Bibo.common.response.APIException;
import com.Bibo.common.response.Response;
import com.Bibo.common.constant.ResponseEnum;
import com.Bibo.common.response.ServiceException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.management.ServiceNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**
 * 全局处理响应数据
 */
@Slf4j
@NoArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler{

    /**
     * 400 - 自定义
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(APIException.class)
    public Response<String> APIExceptionHandler(APIException e) {
        return Response.error(e.getMsg());
    }

    /**
     * 400 - 自定义
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public Response<String> ServiceExceptionHandler(ServiceException e) {
        return Response.error(e.getMsg());
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = "缺少请求参数！";
        log.error(msg, e);
        return Response.error(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.error("参数绑定失败:", e);
        return Response.error(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleServiceException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.iterator().next().getMessage();
        log.error("参数验证失败:", e);
        return Response.error(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        log.error("参数验证失败：", e);
        return Response.error(msg);
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = "不支持当前请求方法！";
        log.error(msg, e);
        return Response.custom(ResponseEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        String msg = "不支持当前媒体类型！";
        log.error(msg, e);
        return Response.custom(ResponseEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 422 - UNPROCESSABLE_ENTITY
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response handleMaxUploadSizeExceededException(Exception e) {
        String msg = "所上传文件大小超过最大限制，上传失败！";
        log.error(msg, e);
        return Response.custom(ResponseEnum.UNPROCESSABLE_ENTITY);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceNotFoundException.class)
    public Response handleServiceException(ServiceNotFoundException e) {
        String msg = "服务内部异常：" + e.getMessage();
        log.error(msg, e);
        return Response.custom(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        String msg = "服务内部异常！" + e.getMessage();
        log.error(msg, e);
        return Response.custom(ResponseEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理参数绑定异常，并拼接出错的参数异常信息。
     * @param result
     */
    private String handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            return fieldErrors.iterator().next().getDefaultMessage();
        }
        return null;
    }

}
