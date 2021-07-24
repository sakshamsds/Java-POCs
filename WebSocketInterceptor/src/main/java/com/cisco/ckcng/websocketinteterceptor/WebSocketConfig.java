package com.cisco.ckcng.websocketinteterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	private String subscriptionURL = "/hello";
	
	@Autowired
	private CustomTextHandler handler;
	
	@Autowired
	private CustomInterceptor interceptor;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, subscriptionURL).setAllowedOrigins("*").addInterceptors(interceptor);
	}
	
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		return new ServletServerContainerFactoryBean();
	}

}

