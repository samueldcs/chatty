package me.samueldcs.chatty;

import com.salesforce.grpc.contrib.spring.GrpcServerHost;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChattyApplication {
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ChattyApplication.class, args);
		Thread.currentThread().join();
	}
	
	@Bean(initMethod = "start")
	public GrpcServerHost grpcServerHost() {
		return new GrpcServerHost(8087);
	}

}
