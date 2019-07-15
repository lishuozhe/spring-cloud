package cn.com.micro.consumer.remote.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.micro.consumer.remote.HelloRemote;

@Component
public class HelloRemoteHystrix implements HelloRemote {

	@Override
	public String hello(@RequestParam(value = "name") String name) {
		return "hello" + name + ", 这里是熔断  ";
	}
}