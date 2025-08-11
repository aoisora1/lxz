package com.lxz.core.config;

import com.lxz.common.exception.BusinessException;
import com.lxz.common.exception.ExceptionVo;
import com.lxz.common.exception.ExceptionVoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @Author 86184
 * @Date 2024/10/2 15:58
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ExceptionVoFactory exceptionVoFactory;

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionVo handleBusinessException(BusinessException e) {
        // TODO 更好的异常打印方式
        e.printStackTrace();
        return exceptionVoFactory.getExceptionVo(e);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionVo handleBusinessException(MethodArgumentNotValidException e) {
        // TODO 更好的异常打印方式
        e.printStackTrace();

        StringBuilder sb = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(err -> {
            sb.append(err.getField());
            sb.append("|");
            sb.append(err.getDefaultMessage());
            sb.append(";");
        });
        return exceptionVoFactory.getExceptionVo(new BusinessException("1000", sb.toString()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionVo handleBusinessException(ConstraintViolationException e) {
        // TODO 更好的异常打印方式
        e.printStackTrace();

        return exceptionVoFactory.getExceptionVo(new BusinessException("1000", e.getMessage()));
    }

    // 兜底异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionVo handleException(Exception e) {
        // TODO 更好的异常打印方式
        e.printStackTrace();
        return exceptionVoFactory.getExceptionVo(new BusinessException("500"));
    }
}
