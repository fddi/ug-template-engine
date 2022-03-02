package ug.template.engine.core.util;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Created by liujf on 2022/3/2.
 * 逝者如斯夫 不舍昼夜
 */
public abstract class GroovyObjectUtils {

    private static GroovyObject goCTE;

    public static GroovyObject getInstanceCTE() throws Exception {
        if (goCTE == null) {
            CompilerConfiguration config = new CompilerConfiguration();
            config.setSourceEncoding("UTF-8");
            GroovyClassLoader gcl = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
            goCTE = (GroovyObject) gcl.loadClass("ug.template.engine.core.CombTemplateEngine")
                    .getDeclaredConstructor().newInstance();
        }
        return goCTE;
    }
}
