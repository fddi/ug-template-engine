package ug.template.engine.core.filter;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ug.template.engine.core.CombFilter;
import ug.template.engine.core.launcher.CombContext;

import java.util.Map;

/**
 * $printIf{} 关键字处理器，判断是否返回此行字符串，支持上下文嵌套判断，缩进4空格/1tab
 * Created by liujf on 2017/11/13.
 * 逝者如斯夫 不舍昼夜
 */
public class KeyPrintIfFilter implements CombFilter {
    private final String KEY = "$printIf{";

    @Override
    public String filter(CombContext context, String input, Map<String, Object> params) throws Exception {
        if (!(input.trim().indexOf(KEY) == 0)) return input;
        String block = input.substring(input.indexOf(KEY) + KEY.length(), input.indexOf("}"));
        String content = input.substring(input.indexOf("}") + 1);
        Binding binding = new Binding();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            binding.setProperty(entry.getKey(), entry.getValue());
        }
        GroovyShell shell = new GroovyShell(binding);
        if ((boolean) shell.evaluate(block)) return content;
        return "";
    }

}
