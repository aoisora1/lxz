package com.lxz.common.exception;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Locale;

public interface ExceptionConstants {

    List<Locale> LOCALES = Lists.newArrayList(Locale.CHINA, Locale.US);

    String DESCRIPTION_PREFIX = "description.";

    String SOLUTION_PREFIX = "solution.";
}
