package com.java4all.netty;

import com.java4all.netty.base.server.EchoServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
		EchoServer echoServer = new EchoServer();
		echoServer.test();
		System.out.println("The Netty application has run");
	}

}
