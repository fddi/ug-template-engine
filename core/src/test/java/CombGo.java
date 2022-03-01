import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujf on 2017/11/20.
 * 逝者如斯夫 不舍昼夜
 */
public class CombGo {

    public static void main(String[] args) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("c01", "01");
            params.put("c02", "7");
            params.put("c03", -3);
            params.put("c04", -4);
            params.put("c05", "1,2,3,4,5");
            String str = CombUtil.getInstance().build()
                    .getComb("test.query1", params);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
