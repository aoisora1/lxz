package com.lxz.game.config;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@ConfigurationProperties(prefix = "game.pool")
public class GameProperties implements InitializingBean {
    private final Map<String, GamePoolProperties> configs = new HashMap<>();

    public GamePoolProperties getConfig(String code) {
        return configs.get(code);
    }

    public boolean hasConfig(String code) {
        return configs.containsKey(code);
    }

    @Override
    public void afterPropertiesSet() {
        String prefix = "game.pool.configs";
        if (!configs.containsKey("default")) {
            throw new IllegalArgumentException(prefix.concat(".default不能为空"));
        }

        for (String code: configs.keySet()) {
            checkNotZero(configs.get(code).getCoreSize(), prefix.concat(".").concat(code).concat(".core-size"));
            checkNotZero(configs.get(code).getMaxSize(), prefix.concat(".").concat(code).concat(".max-size"));
            checkNotZero(configs.get(code).getKeepAliveTime(), prefix.concat(".").concat(code).concat(".keep-alive-time"));
        }
    }

    private void checkNotZero(int v, String field) {
        if (v == 0) {
            throw new IllegalArgumentException(field + " is zero");
        }
    }
}
