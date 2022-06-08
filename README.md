这里有瓜 ^-^

JAVA字符串模板引擎；便捷的工具调用返回、支持Groovy模板引擎。

目前使用它来做SQL字符串模板，配合HQL或NamedParameterJdbcTemplate使用，真香！

如果厌倦了XML繁琐的嵌套格式，值得尝试一下。

## 用法

1. 引用该项目包。
2. 在resource/template下创建一个test.utp文件。格式如图

<img src="https://gitee.com/fddi/ug-template-engine/raw/master/docs/e1.png" width="60%">

   使用IDEA，可以下载插件 [Ug template File](https://plugins.jetbrains.com/plugin/18710-ug-template-file/versions/stable/182388)，高亮语法

3. 调用模板
```java
Map<String, Object> params = new HashMap<>();
params.put("c01", "something1");
params.put("c02", "something2");
params.put("c03", -1);
params.put("c04", 1);
params.put("c05", "1,2,3,4,5");
String sql = new CombFactory().build()
        .getComb("test.query1", params);
System.out.println(sql);
```

   查看打印的sql是否符合你的预期。

4. 如果你使用JdbcTemplate
```java
namedParameterJdbcTemplate.query(sql, params);
```

## 模板API

1. GStringTemplateEngine 

    模板内容支持groovy语言GStringTemplateEngine ，[查看用法](http://www.groovy-lang.org/templating.html#_gstringtemplateengine)

2. $printIf{exp}

    UG模板引擎是简单的按行解析字符串，大括号中表达式为groovy语法，返回bol值；为true时解析行后字符串，否则忽略。

    支持缩进语法，实现嵌套判断
```
$printIf{c02 !=null && !"".equals(c02)}and c02 like '%'||:c02||'%'
    $printIf{c03 > 0}and c03 = :c03
        and 1=1
```

3. $array{param}

    如字符串"1,2,3,4,5"转换为'1','2','3','4','5'

4. $include{fileName.sectionName}

    可以引用其他模板段

## 扩展

- 指定模板utp文件搜索路径
```java
new CombFactory().fileScan(String... scanPackages)
```

- 可以添加自定义筛选器
> 注意是按行解析字符串

```java
new CombFactory().bind(CombFilter... filters)
```

- 可以自定义缓存机制，文件读取到的字符串自定义缓存
```java
new CombFactory().cache(CombCache cache)
```
