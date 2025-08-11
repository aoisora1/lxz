package com.lxz.game;

import com.lxz.game.context.EndContext;
import com.lxz.game.context.StartContext;
import com.lxz.game.context.StepContext;
import com.lxz.game.context.StepResultContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameExecutor {
    private final Logger logger = LoggerFactory.getLogger(GameExecutor.class);

    @Autowired
    private GamePoolManager manager;

    public void newGame(StartContext context) {
        logger.info("新建游戏，对局信息: {}", context.getStartInfo());
        manager.getPool(context.getCode()).newGame(context);
    }

    public StepResultContext step(int code, int id, StepContext context) {
        logger.info("对局{}进行操作{}", id, context.getStepInfo());
        Game game = manager.getPool(code).get(id);
        checkGame(game);
        StepResultContext result = game.step(context);
        if (StringUtils.isNotEmpty(result.getResultInfo())) {
            logger.info("{}", result.getResultInfo());
        }
        if (result.isFinish()) {
            endGame(code, id, new EndContext());
        }
        return result;
    }

    public void endGame(int code, int id, EndContext context) {
        logger.info("对局{}结束", id);
        Game game = manager.getPool(code).get(id);
        checkGame(game);
        GameInfo gameInfo = game.endGame(context);
        // TODO 保存对局信息
        manager.getPool(code).release(id);
        logger.info("释放对局{}", id);
        // TODO 测试用后续删除，改为定时打印或者前端查询，或者指标
        manager.printGameInfo();
    }

    private void checkGame(Game game) {
        if (game == null || !game.isGaming()) {
            String msg = game == null ? "对局不存在" : "对局已结束";
            logger.error(msg);
            throw new GameException("对局不存在或已结束");
        }
    }
}
