package testapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class TestApp {
	public static void main(String[] args) throws URISyntaxException, InterruptedException {
		// subscription URL
		final URI url = new URI("ws://localhost:8080/subscribe?access_token=abc");

		Map<String, String> headers = new HashMap<>();
//		headers.put("access_token", "e3a611fa-db87-3640-bcb3-cab0480a2624");
		headers.put("Authorization", "bearer 9af7402077f80335d298649cb30e6f8ad23491aca77d4b4eaa64fe55b37bc3e8");

		Draft draft = new Draft_6455();
		WebSocketClient client = new WebSocketClient(url, draft, headers, 0) {
			@Override
			public void onOpen(ServerHandshake handshakedata) {
			}
			@Override
			public void onMessage(String message) {
				// printing the messages received in console
				System.out.println("received msg -> " + message);
			}
			@Override
			public void onClose(int code, String reason, boolean remote) {
			}
			@Override
			public void onError(Exception ex) {
			}
		};

		System.out.println("Trying to connect......");
		// establish the connection
		client.connectBlocking();

		// check if connection is established or not
		System.out.println("connected: " + client.isOpen());

		if (client.isOpen()) {
			// if connection is open, then we send a payload
			client.send("send payload here");
			// Timer to keep the connection open (in ms)
			Thread.sleep(60000);
		}

		System.out.println("Closing Connection......");
		// close a connection
		client.closeBlocking();
	}

}