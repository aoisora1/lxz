package com.lxz.game.gobang;

import com.lxz.common.IntegrationTest;
import com.lxz.game.GameEnum;
import com.lxz.game.GameExecutor;
import com.lxz.game.GamePoolManager;
import com.lxz.game.context.EndContext;
import com.lxz.game.games.gobang.context.GobangStartContext;
import com.lxz.game.games.gobang.context.GobangStepContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class GobangTest {
    @Autowired
    private GameExecutor executor;

    @Autowired
    private GamePoolManager manager;

    @Test
    public void test() {
        int id = 1;
        executor.newGame(new GobangStartContext(id, 0, 1));
        executor.newGame(new GobangStartContext(2, 0, 1));
        executor.newGame(new GobangStartContext(3, 0, 1));
        executor.endGame(GameEnum.gobang.getCode(), 3, new EndContext());
        executor.newGame(new GobangStartContext(4, 0, 1));
        executor.newGame(new GobangStartContext(5, 0, 1));

        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(0, 0, 0));
        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(1, 1, 0));

        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(0, 0, 1));
        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(1, 1, 1));

        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(0, 0, 2));
        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(1, 1, 2));

        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(0, 0, 3));
        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(1, 1, 3));

        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(0, 0, 4));
        executor.newGame(new GobangStartContext(6, 0, 1));

    }

    @Test
    public void test2() {
        start(0, 0, 1);
        printGame(0);

        start(1, 0, 1);
        printGame(1);

        start(2, 0, 1);
        printGame(2);
        step(2, 0, 0, 0);
        end(2);

        start(3, 0, 1);
        printGame(3);
        step(3, 0, 0, 0);
        end(3);
    }

    @Test
    public void test3() {
        start(0, 0, 1);
        printGame(0);
        end(0);

        start(1, 0, 1);
        printGame(1);
        end(1);

        start(2, 0, 1);
        printGame(2);

        start(3, 0, 1);
        printGame(3);
    }

    private void start(int id, int p0, int p1) {
        executor.newGame(new GobangStartContext(id, p0, p1));
    }

    private void step(int id, int pid, int x, int y) {
        executor.step(GameEnum.gobang.getCode(), id, new GobangStepContext(pid, x, y));
    }

    private void end(int id) {
        executor.endGame(GameEnum.gobang.getCode(), id, new EndContext());
    }

    private void printGame(int id) {
        System.out.println(manager.getPool(GameEnum.gobang.getCode()).get(id));
    }
}
