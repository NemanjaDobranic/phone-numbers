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
        server.createContext("/api/v1/phone-numbers/autocomplete", new MyHandler());
        //Thread control is given to executor service.
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
    }
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "There will be numbers :)";
            long threadId = Thread.currentThread().getId();
            System.out.println("I am thread " + threadId );
            response = response + "Thread Id = "+threadId;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}