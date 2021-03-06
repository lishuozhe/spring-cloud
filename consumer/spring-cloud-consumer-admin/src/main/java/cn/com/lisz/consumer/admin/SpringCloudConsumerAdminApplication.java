package cn.com.lisz.consumer.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "cn.com.lisz")
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class SpringCloudConsumerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsumerAdminApplication.class, args);
	}

}