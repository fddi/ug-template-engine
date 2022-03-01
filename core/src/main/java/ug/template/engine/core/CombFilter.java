package ug.template.engine.core;

import ug.template.engine.core.launcher.CombContext;

import java.util.Map;

/**
 * 过滤器
 * Created by liujf on 2017/11/13.
 * 逝者如斯夫 不舍昼夜
 */
public interface CombFilter {


    /**
     * @param context 当前环境
     * @param input   输入
     * @param params  参数
     * @return 输出
     * @throws Exception except
     */
    String filter(CombContext context, String input, Map<String, Object> params) throws Exception;

}
