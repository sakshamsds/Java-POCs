package websocketserver.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomTextHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomTextHandler.class);

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("handleTextMessage: Enter. Session Id {}", session.getId());
		ObjectMapper mapper = new ObjectMapper();
		String json = "some json string";
		JsonNode msg = mapper.readTree(json);
		TextMessage m = new TextMessage(msg.toString());

		session.sendMessage(m);
		logger.info("Message Sent: {}", msg);
		Thread.sleep(2000);

		for (int i = 0; i < 5; i++) {
			session.sendMessage(m);
			logger.info("Message Sent: {}", msg);
			Thread.sleep(2000);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("afterConnectionEstablished: Enter. Session Id {}", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("afterConnectionClosed: Enter. Session Id {} and status {}", session.getId(), status);
	}

}
