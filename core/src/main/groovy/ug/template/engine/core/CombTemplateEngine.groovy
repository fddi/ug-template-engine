package ug.template.engine.core

import groovy.text.GStringTemplateEngine

/**
 * Created by liujf on 2022/3/1.
 * 逝者如斯夫 不舍昼夜
 */
class CombTemplateEngine {

    public String make(String str, Map params) {
        def engine = new GStringTemplateEngine()
        def template = engine.createTemplate(str).make(params)
        return template.toString()
    }

}
