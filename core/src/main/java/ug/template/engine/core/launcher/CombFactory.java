package ug.template.engine.core.launcher;

import ug.template.engine.core.CombCache;
import ug.template.engine.core.CombFilter;

import java.util.Arrays;

/**
 * 管道创建工厂
 * Created by liujf on 2017/11/10.
 * 逝者如斯夫 不舍昼夜
 */
public class CombFactory {
    private CombConfig config;
    private CombExecutor combExecutor;

    public CombFactory() {
        config = new CombConfig();
        config.setScanPackages(new String[]{CombConfig.DEFAULT_PATH});
    }

    public CombFactory fileScan(String... scanPackages) {
        config.setScanPackages(scanPackages);
        return this;
    }

    public CombFactory bind(CombFilter... filters) {
        config.getFilters().addAll(Arrays.asList(filters));
        return this;
    }

    public CombFactory cache(CombCache cache) {
        if (cache != null) config.setCombCache(cache);
        return this;
    }

    public CombExecutor build() throws Exception {
        if (config.getScanPackages() == null)
            throw new Exception("comb:Package path is undefined");
        if (combExecutor == null) combExecutor = new CombExecutor(config);
        return combExecutor;
    }

}
