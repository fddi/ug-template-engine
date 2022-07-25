import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujf on 2017/11/20.
 * 逝者如斯夫 不舍昼夜
 */
public class CombGo {

    public void html() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("title", "something title");
            params.put("time", "2022/7/25");
            String[] contents = new String[]{
                    "something item 1", "something item 2", "something item 3"
            };
            params.put("contents", contents);
            String str = CombUtil.getInstance()
                    .build()
                    .getComb("test.html", params);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sql() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("tag1", "表1");
            params.put("tag2", "表2");
            params.put("name", "赵");
            params.put("sex", "");
            params.put("age", "20,30");
            String str = CombUtil.getInstance().fileScan("sql-template")
                    .build()
                    .getComb("sqltest.query1", params);

            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CombGo go = new CombGo();
        go.html();
        go.sql();
    }

}
