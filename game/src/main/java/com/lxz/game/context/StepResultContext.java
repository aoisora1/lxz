package com.lxz.game.context;

import lombok.Getter;

@Getter
public abstract class StepResultContext extends GameContext {
    private boolean invalidStep; // 该步骤是否无效
    private String invalidMessage;
    private boolean finish;

    public StepResultContext(boolean invalidStep, String invalidMessage) {
        this.invalidStep = invalidStep;
        this.invalidMessage = invalidMessage;
    }

    public StepResultContext(boolean finish) {
        this.finish = finish;
    }

    public String getResultInfo() {
        if (invalidStep) {
            return String.format("无效操作，%s", invalidMessage);
        }
        if (finish) {
            return "对局结束,".concat(getFinishInfo());
        }
        return getStepResultInfo();
    }

    protected abstract String getStepResultInfo();

    protected abstract String getFinishInfo();

}
