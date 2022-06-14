package ug.template.engine.core.pipe;

import ug.template.engine.core.CombFilter;
import ug.template.engine.core.CombPipe;
import ug.template.engine.core.launcher.CombBlock;
import ug.template.engine.core.launcher.CombContext;

import java.util.List;
import java.util.Map;

/**
 * 主管道
 * Created by liujf on 2017/11/13.
 * 逝者如斯夫 不舍昼夜
 */
public class CustomCombPipe implements CombPipe {
    private CombContext context;
    private String input;
    private Map<String, Object> params;
    private String output;
    private Integer blockLevel = 0;
    private boolean result = true;

    @Override
    public CombPipe input(CombContext context, String data, Map<String, Object> params) {
        this.context = context;
        this.input = data;
        this.params = params;
        return this;
    }

    @Override
    public CombPipe bind(List<CombFilter> filters) throws Exception {
        blockLevel = getBlockLevel(input);
        result = getResult(context.getBlocks(), blockLevel);
        if (!result) {
            output = "";
            return this;
        }
        output = input;
        for (int i = 0; i < filters.size(); i++) {
            CombFilter filter = filters.get(i);
            output = filter.filter(context, input, params);
            if (!input.equals(output)) {
                break;
            }
        }
        if (output == null || "".equals(output)) {
            result = false;
        }
        return this;
    }

    @Override
    public String output() {
        return output;
    }

    @Override
    public boolean result() {
        return result;
    }

    @Override
    public Integer blockLevel() {
        return blockLevel;
    }

    private Integer getBlockLevel(String input) {
        int blockLevel = 0;
        if (input == null || "".equals(input)) {
            return blockLevel;
        }
        String[] chars = input.split("");
        int bSum = 0;
        for (int i = 0; i < chars.length; i++) {
            if (" ".equals(chars[i])) {
                bSum += 1;
            } else if ("\t".equals(chars[i])) {
                blockLevel += 1;
            } else {
                break;
            }
        }
        blockLevel = blockLevel + (bSum / 4);
        return blockLevel;
    }

    private boolean getResult(List<CombBlock> blocks, Integer blockLevel) {
        if (blocks == null || blocks.size() == 0) {
            return true;
        }
        for (int i = blocks.size() - 1; i >= 0; i--) {
            CombBlock block = blocks.get(i);
            Integer level = block.getLevel();
            boolean result = block.isResult();
            if (level < blockLevel) {
                return result;
            }
        }
        return true;
    }
}
