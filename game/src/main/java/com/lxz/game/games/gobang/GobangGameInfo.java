package com.lxz.game.games.gobang;

import com.lxz.game.GameInfo;

public class GobangGameInfo extends GameInfo {
    private int p0;
    private int p1;
    private int win;

    public GobangGameInfo(int id, int p0, int p1, int win, long startTime, long endTime) {
        super(id, startTime, endTime);
        this.p0 = p0;
        this.p1 = p1;
        this.win = win;
    }
}
