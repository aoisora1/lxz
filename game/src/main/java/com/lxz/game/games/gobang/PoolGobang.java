package com.lxz.game.games.gobang;

import com.lxz.game.context.EndContext;

import java.util.Arrays;

public class PoolGobang extends Gobang {

    public void clear() {
        for (int[] ints : chessboard) {
            Arrays.fill(ints, 0);
        }
        deque.clear();
        winPlayer = null;
        p0 = null;
        p1 = null;
    }

    @Override
    protected void doEndGame(EndContext context) {
        clear();
    }
}
