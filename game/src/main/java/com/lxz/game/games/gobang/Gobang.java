package com.lxz.game.games.gobang;

import com.lxz.game.*;
import com.lxz.game.context.EndContext;
import com.lxz.game.context.StartContext;
import com.lxz.game.context.StepContext;
import com.lxz.game.context.StepResultContext;
import com.lxz.game.games.gobang.context.GobangStartContext;
import com.lxz.game.games.gobang.context.GobangStepContext;
import com.lxz.game.games.gobang.context.GobangStepResultContext;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * 五子棋
 */
public class Gobang extends Game {
    protected Player p0;
    protected Player p1;
    protected Player winPlayer;
    protected Deque<Step> deque; // 对局落子信息
    protected int[][] chessboard; // 棋盘，一层横，二层纵
    private final int xCount = 20; // 棋盘横
    private final int yCount = 20; // 棋盘竖
    private final int winCount = 5;

    public Gobang() {
        init();
    }

    @Override
    public void startGame(StartContext context) {
        super.startGame(context);
        this.p0 = new Player(((GobangStartContext) context).getId0(), PlayerEnum.P_0);
        this.p1 = new Player(((GobangStartContext) context).getId1(), PlayerEnum.P_1);
    }

    @Override
    public StepResultContext step(StepContext context) {
        int pId = ((GobangStepContext)context).getPId();
        int x = ((GobangStepContext)context).getX();
        int y = ((GobangStepContext)context).getY();

        Step step = new Step(getP(pId), x, y);
        String checkResult = checkStep(step);
        if (StringUtils.isNotEmpty(checkResult)) {
            return new GobangStepResultContext(checkResult);
        }

        doStep(step);
        boolean win = win(step);
        GobangStepResultContext resultContext;
        if (win) {
            resultContext = new GobangStepResultContext(win, false, winPlayer.id);
        } else {
            // TODO 棋盘是否已满
            resultContext = new GobangStepResultContext(false, false, -1);
        }

        // TODO 测试用后续删除
        printQiPan();
        return resultContext;
    }

    @Override
    protected void doEndGame(EndContext context) {
        Arrays.fill(chessboard, null);
        chessboard = null;
        deque.clear();
        deque = null;
        winPlayer = null;
        p0 = null;
        p1 = null;
    }

    @Override
    public GameInfo generateGameInfo(EndContext context) {
        // TODO 落子信息等其他信息
        return new GobangGameInfo(getId(), p0.id, p1.id, winPlayer == null ? -1 : winPlayer.id, getStartTime(), getEndTime());
    }

    private void printQiPan() {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (chessboard[i][j] == 0) {
                    System.out.print(" |");
                } else {
                    System.out.print(PlayerEnum.convertColor(chessboard[i][j]) + "|");
                }
            }
            System.out.println();
        }
    }

    private void doStep(Step step) {
        deque.addLast(step);
        chessboard[step.x][step.y] = step.p.playerEnum.getV();
    }

    private String checkStep(Step step) {
        // 位置已落子
        if (chessboard[step.x][step.y] != 0) {
            return "落子无效";
        }
        // 没有交替落子
        if (!deque.isEmpty() && deque.getLast().p == step.p) {
            return "落子无效";
        }
        // 不是先手方率先落子
        if (deque.isEmpty() && step.p.playerEnum != PlayerEnum.P_0) {
            return "落子无效";
        }
        if (winPlayer != null) {
            return "对局已结束";
        }
        return null;
    }

    private boolean valid(int x, int y) {
        return x >= 0 && x < xCount && y >= 0 && y < yCount;
    }

    private Player getP(int id) {
        if (p0.id == id) {
            return p0;
        }
        if (p1.id == id) {
            return p1;
        }
        throw new RuntimeException("对局异常");
    }

    private void init() {
        deque = new ArrayDeque<>();
        initChessboard();
    }

    private void initChessboard() {
        chessboard = new int[xCount][];
        for (int i = 0; i < xCount; i++) {
            chessboard[i] = new int[yCount];
        }
    }

    private boolean win(Step last) {
        boolean win = checkWin(last);
        if (win) {
            winPlayer = last.p;
        }
        return win;
    }

    private boolean checkWin(Step last) {
        int count = 0;
        for (FangXiang f : FangXiang.half) {
            count = 1;
            count += count(last.p.playerEnum.getV(), last.x, last.y, f);
            count += count(last.p.playerEnum.getV(), last.x, last.y, FangXiang.huBu(f));
            if (count >= winCount) {
                return true;
            }
        }
        return false;
    }

    // 判断棋子某个方向上与其相连的同色棋子
    private int count(int v, int x, int y, FangXiang f) {
        Pair<Integer, Integer> next = FangXiang.next(x, y, f);
        int nx = next.getLeft();
        int ny = next.getRight();
        if (!valid(nx, ny)) {
            return 0;
        }
        if (chessboard[nx][ny] == v) {
            return count(v, nx, ny, f) + 1;
        } else {
            return 0;
        }
    }

    // TODO
    // 生成对局信息，用于回溯对局，设计算法，压缩下棋步骤，步骤回溯
    // 悔棋功能
    // 棋盘满的情况

    class Step {
        Player p;
        int x;
        int y;

        public Step(Player p, int x, int y) {
            this.p = p;
            this.x = x;
            this.y = y;
        }
    }

    enum FangXiang {
        F_0(), // 右
        F_1(), // 右上
        F_2(), // 上
        F_3(), // 左上
        F_4(), // 左
        F_5(), // 左下
        F_6(), // 下
        F_7(), // 右下
        ;

        static List<FangXiang> half = Lists.newArrayList(F_0,  F_1, F_2, F_3);

        public static FangXiang huBu(FangXiang f) {
            switch (f) {
                case F_0:
                    return F_4;
                case F_1:
                    return F_5;
                case F_2:
                    return F_6;
                case F_3:
                    return F_7;
            }
            throw new RuntimeException("对局异常");
        }

        public static Pair<Integer, Integer> next(int x, int y, FangXiang f) {
            switch (f) {
                case F_0:
                    return Pair.of(x + 1, y);
                case F_1:
                    return Pair.of(x + 1, y + 1);
                case F_2:
                    return Pair.of(x, y + 1);
                case F_3:
                    return Pair.of(x - 1, y + 1);
                case F_4:
                    return Pair.of(x - 1, y);
                case F_5:
                    return Pair.of(x - 1, y - 1);
                case F_6:
                    return Pair.of(x, y - 1);
                case F_7:
                    return Pair.of(x + 1, y - 1);
            }
            throw new RuntimeException("对局异常");
        }
    }
}
