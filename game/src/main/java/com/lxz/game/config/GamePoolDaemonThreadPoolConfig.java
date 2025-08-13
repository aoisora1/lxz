package com.lxz.game.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lxz.game.GameEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @Description 游戏池守护线程池
 * @Author aoi
 * @Date 2025/8/13
 */
@Configuration
public class GamePoolDaemonThreadPoolConfig {

    @Bean("gamePoolDaemonThreadPool")
    public ScheduledThreadPoolExecutor gamePoolDaemonThreadPool() {
        ThreadFactory tFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("game-pool-daemon").build();
        return new ScheduledThreadPoolExecutor(GameEnum.values().length, tFactory);
    }
}
