package com.lxz.game;

import com.lxz.game.context.EndContext;
import com.lxz.game.context.StartContext;
import com.lxz.game.context.StepContext;
import com.lxz.game.context.StepResultContext;
import lombok.Getter;

@Getter
public abstract class Game {
    private int id;
    private int code;
    private long startTime;
    private long endTime;
    private boolean gaming;

    public void startGame(StartContext context) {
        this.id = context.getId();
        this.code = context.getCode();
        this.startTime = System.currentTimeMillis();
        this.endTime = -1;
        this.gaming = true;
    }

    public abstract StepResultContext step(StepContext context);

    public GameInfo endGame(EndContext context) {
        this.gaming = false;
        this.endTime = System.currentTimeMillis();
        GameInfo info = generateGameInfo(context);
        doEndGame(context);
        return info;
    }

    protected abstract void doEndGame(EndContext context);

    protected abstract GameInfo generateGameInfo(EndContext context);
}
