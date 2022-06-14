package ug.template.engine.core.filter;

import ug.template.engine.core.CombFilter;
import ug.template.engine.core.launcher.CombContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $array{} 关键字处理器
 * Created by liujf on 2022/1/24.
 * 逝者如斯夫 不舍昼夜
 */
public class KeyArrayFilter implements CombFilter {
    private final String KEY = "$array{";

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
            StringBuilder array = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (block.trim().equals(entry.getKey())) {
                    array = new StringBuilder(String.valueOf(entry.getValue()));
                    break;
                }
            }
            String[] values = array.toString().split(",");
            array = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                String str = "',";
                if (i == values.length - 1) {
                    str = "'";
                }
                array.append("'").append(values[i]).append(str);
            }
            Map<String, String> map = new HashMap<>();
            map.put("block", block);
            map.put("array", array.toString());
            list.add(map);
            index = input.indexOf(KEY, r);
        }
        String output = input;
        for (Map<String, String> map : list) {
            output = output.replace(KEY + map.get("block") + "}", map.get("array"));
        }
        return output;
    }
}
