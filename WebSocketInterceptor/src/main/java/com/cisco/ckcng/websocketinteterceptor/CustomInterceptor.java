package com.cisco.ckcng.websocketinteterceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class CustomInterceptor implements HandshakeInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		logger.info("Inside before handshake");
		logger.info("headers {}", request.getHeaders());
		
		if(request.getHeaders().get("accessToken").get(0).equals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ91")) {
			logger.info("Connection Accepted: Valid Access Token");
			return true;
		}else {
			logger.info("Connection Refused: Invalid Access Token");
			return false;
		}
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		logger.info("Inside after handshake");
	}

}
