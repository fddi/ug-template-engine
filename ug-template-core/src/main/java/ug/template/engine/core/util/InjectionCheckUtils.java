package ug.template.engine.core.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liujf on 2022/3/11.
 * 逝者如斯夫 不舍昼夜
 */
public abstract class InjectionCheckUtils {
    static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * 判断字符串是否为类sql语句
     *
     * @param str 字符串
     * @return bol
     */
    public static boolean sqlCheck(String str) {
        Matcher matcher = sqlPattern.matcher(str);
        return matcher.find();
    }

    public static void check(Map<String, Object> params) throws Exception {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (sqlCheck(String.valueOf(entry.getValue()))) {
                throw new Exception(String.format("the params [%s] have sql injection risk ! value [%s]",
                        entry.getKey(), entry.getValue()));
            }
        }
    }

}
