import ug.template.engine.core.launcher.CombFactory;

/**
 * Created by fddiljf on 2017/12/3.
 * 逝者如斯夫 不舍昼夜
 */
public abstract class CombUtil {
    private static CombFactory factory;

    public static CombFactory getInstance() {
        if (factory == null) {
            synchronized (CombUtil.class) {
                if (factory == null) {
                    factory = new CombFactory();
                }
            }
        }
        return factory;
    }
}
