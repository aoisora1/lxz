package com.lxz.game.games.gobang.context;

import com.lxz.game.context.StepContext;
import lombok.Getter;

@Getter
public class GobangStepContext extends StepContext {
    private int pId;
    private int x;
    private int y;

    public GobangStepContext(int pId, int x, int y) {
        this.pId = pId;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getStepInfo() {
        return String.format("玩家%d下棋%d,%d", pId, x, y);
    }
}
