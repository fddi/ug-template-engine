package ug.template.engine.core.launcher;

import ug.template.engine.core.CombPipe;
import ug.template.engine.core.pipe.CustomCombPipe;
import ug.template.engine.core.util.GroovyObjectUtils;
import ug.template.engine.core.util.InjectionCheckUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管道-过滤器 控制类
 * Created by liujf on 2017/11/13.
 * 逝者如斯夫 不舍昼夜
 */

public class CombExecutor {
    private final CombConfig config;

    CombExecutor(CombConfig config) {
        this.config = config;
    }

    /**
     * 返回组合处理后的字符串数据
     *
     * @param itemName 查询章节，文件名.章节名 fileName.sectionName
     * @param params   参数
     * @return 处理后的字符串数据
     * @throws Exception exception
     */
    public String getComb(String itemName, Map<String, Object> params) throws Exception {
        if (itemName == null) {
            throw new Exception("comb:itemName can not null!");
        }
        if (config.isInjectionCheck() && params != null) {
            InjectionCheckUtils.check(params);
        }
        String[] tags = itemName.trim().split("\\.");
        if (tags.length <= 1) throw new Exception("comb:can not find sectionName!");
        String fileName = tags[0];
        String sectionName = tags[1];
        BufferedReader reader = null;
        boolean cached = true;
        if (config.getCombCache() != null) {
            String cache = config.getCombCache().getCache(itemName);
            if (cache != null && !"".equals(cache)) {
                ByteArrayInputStream is = new ByteArrayInputStream(cache.getBytes());
                reader = new BufferedReader(new InputStreamReader(is));
            }
        }
        if (reader == null) {
            cached = false;
            InputStream inputStream = this.getFileIs(fileName);
            if (inputStream == null) throw new Exception("comb:can not load file");
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        }
        StringBuilder nsb = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        boolean isBegin = false;
        boolean isEnd = false;
        List<CombBlock> blocks = new ArrayList<>();
        int row = 0;
        int separatorNum = 0;
        do {
            String tempString = reader.readLine();
            row++;
            if (tempString == null) break;
            if (isComment(tempString)) continue;
            if (!isBegin && (isBegin = this.isBegin(tempString, sectionName))) {
                if (!tempString.contains(CombConfig.KEY_STR_SEPARATOR)) {
                    continue;
                }
            }
            if (!isBegin) continue;
            if (tempString.contains(CombConfig.KEY_STR_SEPARATOR)) {
                if (separatorNum > 0) {
                    isEnd = this.isEnd(tempString);
                    tempString = tempString.substring(0, tempString.indexOf(CombConfig.KEY_STR_SEPARATOR));
                } else {
                    separatorNum++;
                    if (tempString.length() > tempString.indexOf(CombConfig.KEY_STR_SEPARATOR)) {
                        tempString = tempString.substring(tempString.indexOf(CombConfig.KEY_STR_SEPARATOR) + 1);
                    }
                }
            }
            tempString = tempString.replaceAll(CombConfig.KEY_STR_SEPARATOR, "");
            config.getDefaultFilters().addAll(config.getFilters());
            CombPipe pipe = new CustomCombPipe().input(new CombContext(config, itemName, blocks, row), tempString, params)
                    .bind(config.getDefaultFilters());
            String output = pipe.output();
            if (pipe.blockLevel() == 0) {
                blocks = new ArrayList<>();
            }
            CombBlock block = new CombBlock();
            block.setLevel(pipe.blockLevel());
            block.setResult(pipe.result());
            blocks.add(block);
            if (!"".equals(output)) {
                sb.append(output).append("\n\r");
            }
        } while (!isEnd);
        reader.close();
        if (!cached && config.getCombCache() != null) config.getCombCache().setCache(itemName, nsb.toString());
        Object[] obj = new Object[]{sb.toString(), params};
        return (String) GroovyObjectUtils.getInstanceCTE().invokeMethod("make", obj);
    }

    /**
     * 查找文件 预设路径为./template 可配置扫描多个路径
     *
     * @param fileName *.utp 文件名
     * @return file
     */
    private InputStream getFileIs(String fileName) {
        for (String scanPackage : config.getScanPackages()) {
            String path = scanPackage + "/" + fileName + ".utp";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            if (inputStream != null) return inputStream;
        }
        return null;
    }

    /**
     * 判断是否为注释，注释标识为#
     *
     * @param line 行数据
     * @return bol
     */
    private boolean isComment(String line) {
        return line.trim().indexOf(CombConfig.KEY_COMMENT) == 0;
    }

    /**
     * 是否章节起始行
     *
     * @param line        行数据
     * @param sectionName 章节名
     * @return bol
     */
    private boolean isBegin(String line, String sectionName) {
        if (line.trim().indexOf(CombConfig.KEY_DEF) < 0) {
            return false;
        }
        String str = line.substring(line.indexOf(CombConfig.KEY_DEF) + CombConfig.KEY_DEF.length());
        if (str.indexOf(CombConfig.KEY_STR_SEPARATOR) > 0) {
            str = str.substring(0, str.indexOf(CombConfig.KEY_STR_SEPARATOR));
        }
        str = str.replaceAll("\\s+", "");
        return sectionName.equals(str);
    }

    /**
     * 是否章节结束
     *
     * @param line 行数据
     * @return bol 章节是否结束
     * @throws Exception
     */
    private boolean isEnd(String line) throws Exception {
        String str = line.substring(line.indexOf(CombConfig.KEY_STR_SEPARATOR));
        str = str.replaceAll("\\s+", "");
        if (!CombConfig.KEY_STR_SEPARATOR.equals(str)) {
            throw new Exception("comb:the syntax error");
        }
        return true;
    }

}
