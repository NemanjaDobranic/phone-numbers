package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

//Server imports
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

//Query Map imports
import java.util.Map;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.Collectors;

//Regular Expressions
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				Matcher matcher = Pattern.compile("^/api/v1/phone-numbers/autocomplete$")
						.matcher(exchange.getRequestURI().getPath());
				if (matcher.matches()) {
					String response = exchange.getRequestURI().getPath();
					exchange.sendResponseHeaders(200, response.length());
					OutputStream output = exchange.getResponseBody();
					output.write(response.getBytes());
					output.close();
				} else {
					exchange.sendResponseHeaders(404, -1);// 404 Resource Is Not Available
				}

			} else {
				exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
			}
		}
	}

	public static Map<String, String> getParamMap(String query) {
		// query is null if not provided (e.g. localhost/path )
		// query is empty if '?' is supplied (e.g. localhost/path? )
		if (query == null || query.isEmpty())
			return Collections.emptyMap();

		return Stream.of(query.split("&")).filter(s -> !s.isEmpty()).map(kv -> kv.split("=", 2))
				.collect(Collectors.toMap(x -> x[0], x -> x[1]));

	}
}