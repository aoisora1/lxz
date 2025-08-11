package com.lxz.game.games.gobang;

import lombok.Getter;

@Getter
public enum PlayerEnum {
    P_0(0, "先手", "黑", -1),
    P_1(1, "后手", "白", 1);

    private int playerNum;
    private String xh; // 先后手
    private String color; // 棋子颜色
    private int v; // 落子值

    PlayerEnum(int playerNum, String xh, String color, int v) {
        this.playerNum = playerNum;
        this.xh = xh;
        this.color = color;
        this.v = v;
    }

    public static String convertColor(int v) {
        for (PlayerEnum playerEnum : PlayerEnum.values()) {
            if (playerEnum.v == v) {
                return playerEnum.color;
            }
        }
        return "";
    }

}
