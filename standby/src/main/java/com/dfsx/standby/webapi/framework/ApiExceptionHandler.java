package com.dfsx.standby.webapi.framework;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by YC on 2019/9/29.
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorInformation handleBadException(HttpServletRequest request, Exception ex) {
        if (ex instanceof NoHandlerFoundException) {
            return new ErrorInformation(404, "api url not found");
        }

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new ErrorInformation(404, "api not support this method");
        }

        if (ex instanceof MethodArgumentTypeMismatchException || ex instanceof MissingServletRequestParameterException) {
            return new ErrorInformation(500, "invaild api url parameters");
        }

        if (ex instanceof HttpMessageNotReadableException) {
            return new ErrorInformation(500, "invaild request body parameters");
        }

        if (ex instanceof AuthenticationException) {
            return new ErrorInformation(400, ex.getMessage());
        }

        if (ex instanceof BindException) {
            BindException exception = (BindException) ex;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            return new ErrorInformation(400, errors.get(0).getDefaultMessage());
        }
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            return new ErrorInformation(400, errors.get(0).getDefaultMessage());
        }
        return new ErrorInformation(500, "服务器异常");
    }
}
