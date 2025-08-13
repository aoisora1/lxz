package com.lxz.game.config;

import lombok.Data;

@Data
public class GamePoolProperties {
    private int coreSize;
    private int maxSize;
    private int keepAliveTime; // 分
}
