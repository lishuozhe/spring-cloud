# spring-cloud
spring-cloud微服务架构

### 服务提供者
```
基础服务，包括权限管理，角色管理，用户管理等
实现基本数据库操作，为业务模块提供服务，不对外访问。
如货主服务，车主服务，订单服务等

```

#### 查询条件使用
查询, 更新, 删除处理条件判断, 默认使用 
``` List<RequestCondition> ``` 
, 暂时只支持AND, OR未实现<br>
```ReqCondition```类定义为:
```java
public class ReqCondition {
    String type;
    String value;
}
```
type用于指定字段名, value用于指定数值<br>
##### 等于查询
```Arrays.asList(new ReqCondition("name", "name1"))```, 查询字段name等于name1的数据
##### 不等于查询
```Arrays.asList(new ReqCondition("neq_name", "name1"))```, 查询字段name不等于name1的数据
##### 大于查询
```Arrays.asList(new ReqCondition("gt_val", "1"))```, 查询字段val大于1的数据
##### 小于查询
```Arrays.asList(new ReqCondition("lt_val", "1"))```, 查询字段val小于1的数据
##### LIKE查询
```Arrays.asList(new ReqCondition("like_name", "a"))```, 查询字段name包含a的数据
##### IN查询
```Arrays.asList(new ReqCondition("in_val", "1"), new ReqCondition("in_val", "2"))```, 查询字段val是1或2的数据
##### ISNULL查询
```Arrays.asList(new ReqCondition("null_val", ""))```, 查询字段val是null的数据, 无需设值value字段
##### NOT ISNULL查询
```Arrays.asList(new ReqCondition("notnull_val", ""))```, 查询字段val不是null的数据, 无需设值value字段
##### Like查询
```Arrays.asList(new ReqCondition("start_val", "start"))```, 查询字段val是否以start开始
##### Like查询
```Arrays.asList(new ReqCondition("end_val", "end"))```, 查询字段val是否以end结尾
##### 为空查询
```Arrays.asList(new ReqCondition("empty_val", ""))```, 查询字段val是否为空字符串
##### 不为空查询
```Arrays.asList(new ReqCondition("notempty_val", ""))```, 查询字段val是否不为空字符串