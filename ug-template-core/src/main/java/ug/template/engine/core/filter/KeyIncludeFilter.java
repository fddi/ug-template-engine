package ug.template.engine.core.filter;

import ug.template.engine.core.CombFilter;
import ug.template.engine.core.launcher.CombConfig;
import ug.template.engine.core.launcher.CombContext;
import ug.template.engine.core.launcher.CombFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $include{} 关键字处理器
 * Created by liujf on 2017/11/22.
 * 逝者如斯夫 不舍昼夜
 */
public class KeyIncludeFilter implements CombFilter {
    private final String KEY = "$include{";

    @Override
    public String filter(CombContext context, String input, Map<String, Object> params) throws Exception {
        if (!input.contains(KEY)) {
            return input;
        }
        int index = input.indexOf(KEY);
        List<Map<String, String>> list = new ArrayList<>();
        while (index >= 0) {
            int r = input.indexOf("}", index);
            if (r < 0) {
                break;
            }
            String block = input.substring(index + KEY.length(), r);
            if (block.trim().equals(context.getItemName())) {
                throw new Exception(String.format("comb itemName[%s] row [%d]:can not include itself!", context.getItemName(), context.getRow()));
            }
            CombConfig config = context.getConfig();
            List<CombFilter> filters = new ArrayList<>(config.getFilters());
            CombFilter[] arrayFilter = new CombFilter[filters.size()];
            String include = new CombFactory()
                    .fileScan(config.getScanPackages())
                    .bind(filters.toArray(arrayFilter))
                    .cache(config.getCombCache()).build()
                    .getComb(block.trim(), params);
            Map<String, String> map = new HashMap<>();
            map.put("block", block);
            map.put("include", include);
            list.add(map);
            index = input.indexOf(KEY, r);
        }
        String output = input;
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = list.get(i);
            output = output.replace(KEY + map.get("block") + "}", map.get("include"));
        }
        return output;
    }

}
