package com.lxz.game.context;

import lombok.Getter;

@Getter
public abstract class StartContext extends GameContext {
    private int code;
    private int id;

    public StartContext(int code, int id) {
        this.code = code;
        this.id = id;
    }

    public String getStartInfo() {
        return String.format("code:%d,id:%d", getCode(), getId());
    }
}
