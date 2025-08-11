package com.lxz.game.games.gobang.context;

import com.lxz.game.GameEnum;
import com.lxz.game.context.StartContext;
import lombok.Getter;

@Getter
public class GobangStartContext extends StartContext {
    private int id0;
    private int id1;

    public GobangStartContext(int id, int id0, int id1) {
        super(GameEnum.gobang.getCode(), id);
        this.id0 = id0;
        this.id1 = id1;
    }

    @Override
    public String getStartInfo() {
        return super.getStartInfo().concat(String.format(",玩家1:%d,玩家2:%d", getId0(), getId1()));
    }
}
