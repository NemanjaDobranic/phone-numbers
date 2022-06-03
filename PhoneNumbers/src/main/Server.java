package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//Server imports
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

import java.util.List;
import java.util.Map;
//Regular Expressions
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
	private static final int PORT = 8000;

	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private static final int STATUS_OK = 200;
	private static final int STATUS_BAD_REQUEST = 400;
	private static final int STATUS_NOT_FOUND = 404;
	private static final int STATUS_METHOD_NOT_ALLOWED = 405;

	private static final int NO_RESPONSE_LENGTH = -1;

	private static final String METHOD_GET = "GET";

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		server.createContext("/api/v1/phone-numbers/autocomplete", new PhoneHttpHandler());
		// Thread control is given to executor service.
		server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		server.start();
		System.out.println("Server is up and running on port 8000");
	}

	static class PhoneHttpHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			final String requestMethod = exchange.getRequestMethod().toUpperCase();
			switch (requestMethod) {
			case METHOD_GET: {
				final Headers headers = exchange.getResponseHeaders();
				Matcher matcher = Pattern.compile("^/api/v1/phone-numbers/autocomplete$")
						.matcher(exchange.getRequestURI().getPath());
				if (matcher.matches()) {
					headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
					final Map<String, List<String>> requestParameters = Utility
							.getRequestParameters(exchange.getRequestURI());

//					final String responseBody = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
					final List<String> queryList = requestParameters.get("query");
					if (queryList != null) {
						final byte[] rawResponseBody = Utility.getNumbers(queryList.get(0));
						exchange.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
						OutputStream output = exchange.getResponseBody();
						output.write(rawResponseBody);
						output.close();
					} else {
						exchange.sendResponseHeaders(STATUS_BAD_REQUEST, NO_RESPONSE_LENGTH);
					}

				} else {
					exchange.sendResponseHeaders(STATUS_NOT_FOUND, NO_RESPONSE_LENGTH);
				}
			}
			default:
				exchange.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
			}
		}
	}

}