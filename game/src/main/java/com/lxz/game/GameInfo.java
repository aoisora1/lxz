package com.lxz.game;

/**
 * 用于存储对局信息
 */
public class GameInfo {
    private int id;
    private long startTime;
    private long endTime;

    public GameInfo(int id, long startTime, long endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
