# spring-cloud
spring-cloud微服务架构

### 架构图

##### 系统架构图

![系统架构图](document/resources/springcloud架构.png)


### 项目结构

``` lua
spring-cloud
├── spring-cloud-config -- 配置中心（8200）
├── spring-cloud-eureka -- 注册中心（8000）
├── spring-cloud-zuul -- 服务网关（8888）
├── spring-cloud-producer-base -- 基础服务（9000）
├── spring-cloud-consumer-oauth -- 认证中心（10000）
├── spring-cloud-consumer-admin -- 管理中心（9100）
├── spring-cloud-turbine -- 熔断监控（8100）
└── spring-cloud-zipkin -- 链路跟踪（8300）

```