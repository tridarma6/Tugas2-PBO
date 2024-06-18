package com.subscription.httpserver;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.*;
public class ResponseHandler {
    
    public void getResponse(HttpExchange exchange, String jsonObject, String[] path, String table, int statusCode) throws IOException{
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = jsonObject;
        exchange.sendResponseHeaders(statusCode, response.length());
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(statusCode, response.length());
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
