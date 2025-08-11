package com.lxz.game;

import com.lxz.game.context.StartContext;
import com.lxz.game.games.gobang.PoolGobang;
import org.springframework.stereotype.Service;

@Service
public class GameFactory {

    // TODO 开闭
    public Game newGame(StartContext context) {
        GameEnum gameEnum = GameEnum.getGameEnum(context.getCode());
        if (gameEnum == null) {
            throw new RuntimeException("非法数据");
        }
        switch (gameEnum) {
            case gobang:
                return new PoolGobang();
            default:
                throw new RuntimeException("非法数据");
        }
    }
}
