package com.lxz.common.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;

/**
 * @ClassName BusinessException
 * @Description 业务异常
 * @Author 86184
 * @Date 2024/10/1 17:23
 */
@Getter
public class BusinessException extends RuntimeException {
    private String code;
    private Object[] args;

    public BusinessException(String code, Object... args) {
        super(String.format(Locale.ROOT, "code=%s, args=%s", code, Arrays.toString(args)));
        this.code = code;
        this.args = args;
    }
}
