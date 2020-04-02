# spring-cloud
spring-cloud微服务架构

### 服务消费者
```
认证中心，负责用户的认证与鉴权
```
- 认证接口: localhost:10000/oauth/token
或 http://localhost:8888/security/oauth/token?username=admin&password=admin

```
接口参数: 
grant_type=password,
username=,
password=,
client_id=4356a818-2fad-499e-8c61-62d33a5735ed,
client_secret=068v7AgVF057Il5iF3Bz8uW245TW77
```

- 刷新接口: localhost:10000/oauth/token

```
接口参数: 
grant_type=refresh_token,
refresh_token=,
client_id=4356a818-2fad-499e-8c61-62d33a5735ed,
client_secret=068v7AgVF057Il5iF3Bz8uW245TW77
```