package ug.template.engine.core;

/**
 * Created by liujf on 2022/1/21.
 * 逝者如斯夫 不舍昼夜
 */
public interface CombCache {

    /**
     * set cache
     *
     * @param sectionName fileName.SectionName
     * @param data        the Section data
     */
    void setCache(String sectionName, String data);

    /**
     * get cache data
     *
     * @param sectionName fileName.SectionName
     * @return the Section data
     */
    String getCache(String sectionName);
}
