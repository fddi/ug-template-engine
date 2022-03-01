package ug.template.engine.core.launcher;

import java.io.Serializable;

/**
 * Created by liujf on 2022/3/1.
 * 逝者如斯夫 不舍昼夜
 */
public class CombBlock implements Serializable {
    private boolean result;
    private Integer level;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
