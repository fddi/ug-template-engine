这里有瓜 ^-^

JAVA template engine; supports Groovy template engine. Easy to assemble strings.

JAVA模板引擎；支持Groovy模板引擎。可以方便的组装字符串。

## 用法

1. 添加依赖

maven
```xml
<dependency>
  <groupId>io.github.fddi</groupId>
  <artifactId>ug-template-core</artifactId>
  <version>0.2.5</version>
</dependency>
```

gradle
```groovy
implementation 'io.github.fddi:ug-template-core:0.2.5'
```
2. 在resource/template下创建一个test.utp文件

<img src="https://gitee.com/fddi/ug-template-engine/raw/master/docs/e1.png" width="60%">

   使用IDEA，可下载插件 [Ug template File](https://plugins.jetbrains.com/plugin/18710-ug-template-file/versions/stable/182388)，高亮语法

3. 调用模板
```java
Map<String, Object> params = new HashMap<>();
params.put("title", "something title");
params.put("time", "2022/7/25");
String[] contents = new String[]{
        "something item 1", "something item 2", "something item 3"
};
params.put("contents", contents);
String html = new CombFactory().build()
        .getComb("test.html", params);
System.out.println(html);
```

   查看打印的html是否符合你的预期。

## SQL模板引擎

可用做SQL模板引擎，配合HQL或NamedParameterJdbcTemplate使用，真香！
已经厌烦了XML繁琐的嵌套格式，值得尝试。
> 模板已对入参SQL注入进行字符串校验。

1. 在resource/sql-template下创建一个sqltest.utp文件。
   
<img src="https://gitee.com/fddi/ug-template-engine/raw/master/docs/e2.png" width="60%">

2. 调用模板
```java
Map<String, Object> params = new HashMap<>();
params.put("tag1", "表1");
params.put("tag2", "表2");
params.put("name", "赵");
params.put("sex", "");
params.put("age", "20,30");
String sql = new CombFactory().fileScan("sql-template")
     .build()
     .getComb("test.html", params);
System.out.println(sql);
```
查看打印的SQL是否符合你的预期。

3. 如果你使用JdbcTemplate
```java
namedParameterJdbcTemplate.query(sql, params);
```

## API

1. GStringTemplateEngine 

    支持groovy模板语法：GStringTemplateEngine ，[查看用法](http://www.groovy-lang.org/templating.html#_gstringtemplateengine)

2. $printIf{exp}

    按行解析字符串，大括号中表达式为groovy语法，返回bol值：为true时解析行后字符串，否则忽略。

    支持缩进语法，实现嵌套判断
```
$printIf{c02 !=null && !"".equals(c02)}and c02 like '%'||:c02||'%'
    $printIf{c03 > 0}and c03 = :c03
        and 1=1
```

3. $array{param}

    如字符串"1,2,3,4,5"转换为'1','2','3','4','5'

4. $include{fileName.sectionName}

    可以引用其他模板

## 配置

- 指定模板utp文件搜索路径
```java
new CombFactory().fileScan(String... scanPackages)
```

- 可以添加自定义解析器
> 注意是按行解析字符串

```java
new CombFactory().bind(CombFilter... filters)
```

- 可以自定义缓存机制，文件读取到的字符串自定义缓存
```java
new CombFactory().cache(CombCache cache)
```
