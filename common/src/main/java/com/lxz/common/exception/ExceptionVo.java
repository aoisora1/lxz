package com.lxz.common.exception;

import lombok.Data;

import java.util.Locale;
import java.util.Map;

/**
 * @ClassName ExceptionVo
 * @Description Exception对象类
 * @Author 86184
 * @Date 2024/10/2 14:32
 */
@Data
public class ExceptionVo {
    private String code;
    private Map<Locale, String> description;
    private Map<Locale, String> solution;
}
