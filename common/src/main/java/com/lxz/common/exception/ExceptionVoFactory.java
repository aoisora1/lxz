package com.lxz.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName ExceptionVoFactory
 * @Description 异常信息处理
 * @Author 86184
 * @Date 2024/10/2 14:30
 */
@Component
public class ExceptionVoFactory {
    private final Logger logger = LoggerFactory.getLogger(ExceptionVoFactory.class);

    @Autowired
    private MessageSource messageSource;

    public ExceptionVo getExceptionVo(BusinessException exception) {
        ExceptionVo exceptionVo = new ExceptionVo();
        exceptionVo.setCode(exception.getCode());

        Map<Locale, String> descriptionMap = new HashMap<>();
        Map<Locale, String> solutionMap = new HashMap<>();
        for (Locale locale : ExceptionConstants.LOCALES) {
            try {
                descriptionMap.put(locale, messageSource.getMessage(ExceptionConstants.DESCRIPTION_PREFIX + exception.getCode(), exception.getArgs(), locale));
            } catch (NoSuchMessageException e) {
                logger.warn("error code {} can not find description", exception.getCode());
            }

            try {
                solutionMap.put(locale, messageSource.getMessage(ExceptionConstants.SOLUTION_PREFIX + exception.getCode(), exception.getArgs(), locale));
            } catch (NoSuchMessageException e) {
                logger.warn("error code {} can not find solution", exception.getCode());
            }
        }

        exceptionVo.setDescription(descriptionMap);
        exceptionVo.setSolution(solutionMap);
        return exceptionVo;
    }
}
