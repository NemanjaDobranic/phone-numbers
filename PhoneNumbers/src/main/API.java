package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class API {
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/api/v1/phone-numbers/autocomplete", new PhoneHttpHandler());
		// Thread control is given to executor service.
		server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		server.start();
		System.out.println("Server is up and running on port 8000");
	}

	static class PhoneHttpHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if ("GET".equals(exchange.getRequestMethod())) {
				String response = "There will be numbers :)";
				exchange.sendResponseHeaders(200, response.length());
				OutputStream output = exchange.getResponseBody();
				output.write(response.getBytes());
				output.close();
			} else {
				exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
			}
		}
	}
}