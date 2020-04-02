# spring-cloud
spring-cloud微服务架构

### 架构图

##### 系统架构图

![系统架构图](document/resources/springcloud架构.png)


### 项目结构

``` lua
spring-cloud
├── spring-cloud-config -- 配置中心（8200）
├── common
	├── spring-cloud-common -- 通用模块
	├── spring-cloud-common-data -- 通用数据模块，服务生产者使用
	├── spring-cloud-common-model -- 通用模型模块，服务生产者和消费者使用
	└── spring-cloud-common-oauth -- 通用权限模块，服务消费者使用
├── spring-cloud-eureka -- 注册中心（8000）
├── spring-cloud-zuul -- 服务网关（8888）
├── producer
	└── spring-cloud-producer-base -- 基础服务（9000）
├── consumer
	├── spring-cloud-consumer-oauth -- 认证中心（10000）
	└── spring-cloud-consumer-admin -- 管理中心（9100）
├── spring-cloud-turbine -- 熔断监控（8100）
└── spring-cloud-zipkin -- 链路跟踪（8300）

```



### 技术选型

#### 后端技术

技术 | 说明 | 官网
----|----|----
Spring cloud | 微服务框架 | 
Spring Boot | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Spring Security | 认证和授权框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)
jpa | ORM框架  | 
Swagger-UI | 文档生产工具 | [https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui)
Hibernator-Validator | 验证框架 | [http://hibernate.org/validator/](http://hibernate.org/validator/)
Elasticsearch | 搜索引擎（暂未集成） | [https://github.com/elastic/elasticsearch](https://github.com/elastic/elasticsearch)
RabbitMq | 消息队列 （暂未集成）| [https://www.rabbitmq.com/](https://www.rabbitmq.com/)
Redis | 分布式缓存（暂未集成） | [https://redis.io/](https://redis.io/)
MongoDb | NoSql数据库（暂未集成） | [https://www.mongodb.com/](https://www.mongodb.com/)
Docker | 应用容器引擎 （暂未集成）| [https://www.docker.com/](https://www.docker.com/)
Druid | 数据库连接池 | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
JWT | JWT登录支持 | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt)
Lombok | 简化对象封装工具 | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok)

### 开发环境

工具 | 版本号 | 下载
----|----|----
JDK | 1.8 | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Mysql | 5.7 | https://www.mysql.com/

### 搭建步骤

> 本地环境搭建

- 启动producer服务会自动创建数据表 或者执行初始化数据库脚本<spring-cloud-base.sql>
- 注册中心访问地址: http://localhost:8000
- 认证中心熔断数据：http://localhost:10000/actuator/hystrix.stream
- 管理中心文档地址: http://localhost:9100/swagger-ui.html#/
- 管理中心熔断数据：http://localhost:9100/actuator/hystrix.stream
- 网关文档地址: http://localhost:8888/swagger-ui.html
- 熔断监控访问地址: http://localhost:8100/hystrix (填入数据地址: http://localhost:8100/turbine.stream)
- 链路跟踪访问地址: http://localhost:8300/zipkin/

> 熔断监控、链路跟踪只在生产环境下配置

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