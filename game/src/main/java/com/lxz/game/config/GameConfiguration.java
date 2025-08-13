package com.lxz.game.config;

import com.lxz.game.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class GameConfiguration {

    @Bean
    public GamePoolManager gamePoolManager(GameFactory gameFactory, GameProperties gameProperties, @Qualifier("gamePoolDaemonThreadPool") ScheduledThreadPoolExecutor executor) {
        Map<Integer, GamePool> gamePoolMap = new HashMap<>();
        for (GameEnum gameEnum : GameEnum.values()) {
            GamePool gamePool;
            if (gameProperties.hasConfig(gameEnum.getEn())) {
                gamePool = new GamePool(gameProperties.getConfig(gameEnum.getEn()), gameFactory, executor);
            } else {
                gamePool = new GamePool(gameProperties.getConfig("default"), gameFactory, executor);
            }
            gamePoolMap.put(gameEnum.getCode(), gamePool);
        }

        return new GamePoolManager(gamePoolMap);
    }
}
