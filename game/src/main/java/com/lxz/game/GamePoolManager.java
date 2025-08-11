package com.lxz.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GamePoolManager {
    private final Logger logger = LoggerFactory.getLogger(GamePoolManager.class);
    private Map<Integer, GamePool> poolMap;

    public GamePoolManager(Map<Integer, GamePool> poolMap) {
        this.poolMap = poolMap;
    }

    public GamePool getPool(int code) {
        return poolMap.get(code);
    }

    public void printGameInfo() {
        for (int code : poolMap.keySet()) {
            logger.info("{} 池信息 {}", GameEnum.getGameEnum(code).getName(),  poolMap.get(code).getGameInfo());
        }
    }
}
