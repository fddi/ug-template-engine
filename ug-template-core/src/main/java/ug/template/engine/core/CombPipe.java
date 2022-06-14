package ug.template.engine.core;

import ug.template.engine.core.launcher.CombContext;

import java.util.List;
import java.util.Map;

/**
 * 管道
 * Created by liujf on 2017/11/13.
 * 逝者如斯夫 不舍昼夜
 */
public interface CombPipe {

    /**
     * input 数据流入
     *
     * @param context 当前环境
     * @param data   数据
     * @param params 数据参数
     * @return pipe
     */
    CombPipe input(CombContext context, String data, Map<String, Object> params);

    /**
     * 绑定处理器
     *
     * @param filters 一组处理器
     * @return pipe
     * @throws Exception except
     */
    CombPipe bind(List<CombFilter> filters) throws Exception;

    /**
     * output 数据流出
     *
     * @return 处理后的数据
     */
    String output();

    /**
     * 处理结果返回
     *
     * @return 处理结果, 如果output为空，则必定返回false
     */
    boolean result();

    /**
     * 块缩进层级
     *
     * @return 层级数
     */
    Integer blockLevel();
}
