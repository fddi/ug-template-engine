package ug.template.engine.core.launcher;

import java.util.List;

/**
 * Created by liujf on 2022/1/24.
 * 逝者如斯夫 不舍昼夜
 */
public class CombContext {
    private CombConfig config;
    private String itemName;
    private List<CombBlock> blocks;
    private Integer row;

    public CombContext(CombConfig config, String itemName, List<CombBlock> blocks, Integer row) {
        this.config = config;
        this.itemName = itemName;
        this.blocks = blocks;
        this.row = row;
    }

    public CombConfig getConfig() {
        return config;
    }

    public void setConfig(CombConfig config) {
        this.config = config;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<CombBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<CombBlock> blocks) {
        this.blocks = blocks;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }
}
