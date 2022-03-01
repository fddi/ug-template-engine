package ug.template.engine.core.launcher;

import ug.template.engine.core.CombCache;
import ug.template.engine.core.CombFilter;
import ug.template.engine.core.filter.KeyArrayFilter;
import ug.template.engine.core.filter.KeyPrintIfFilter;
import ug.template.engine.core.filter.KeyIncludeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件配置类
 * Created by liujf on 2017/11/22.
 * 逝者如斯夫 不舍昼夜
 */
public class CombConfig {
    public static final String KEY_COMMENT = "#";
    public static final String KEY_STR_SEPARATOR = "`";
    public static final String KEY_DEF = "section";
    public static final String DEFAULT_PATH = "template";
    private String[] scanPackages;
    private CombCache combCache;
    private List<CombFilter> defaultFilters = new ArrayList<>();
    private List<CombFilter> filters = new ArrayList<>();

    public CombConfig() {
        defaultFilters.add(new KeyPrintIfFilter());
        defaultFilters.add(new KeyIncludeFilter());
        defaultFilters.add(new KeyArrayFilter());
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
    }

    public List<CombFilter> getDefaultFilters() {
        return defaultFilters;
    }

    public List<CombFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<CombFilter> filters) {
        this.filters = filters;
    }

    public CombCache getCombCache() {
        return combCache;
    }

    public void setCombCache(CombCache combCache) {
        this.combCache = combCache;
    }
}
