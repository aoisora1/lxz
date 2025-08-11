package com.lxz.game.games.gobang.context;

import com.lxz.game.context.StepResultContext;
import lombok.Getter;

@Getter
public class GobangStepResultContext extends StepResultContext {
    private boolean win; // 有一方赢得对局
    private boolean end; // 棋盘已满无法继续对局
    private int winId;

    public GobangStepResultContext(boolean win, boolean end, int winId) {
        super(win || end);
        this.win = win;
        this.end = end;
        this.winId = winId;
    }

    public GobangStepResultContext(String invalidMessage) {
        super(true, invalidMessage);
    }

    @Override
    protected String getStepResultInfo() {
        return "";
    }

    @Override
    protected String getFinishInfo() {
        return end ? "平局" : String.format("赢家为%s", winId);
    }
}
