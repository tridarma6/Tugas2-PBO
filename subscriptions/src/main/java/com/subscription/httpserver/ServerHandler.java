package com.subscription.httpserver;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerHandler implements HttpHandler {
    ResponseHandler responseHandler = new ResponseHandler();
    CustomersReqHandler customersReqHandler = new CustomersReqHandler();
    ItemsReqHandler itemsReqHandler = new ItemsReqHandler();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                if (path.length == 0 || (path.length == 1 && path[0].isEmpty())) {
                    responseHandler.sendResponse(exchange, 200, "Success");
                } else if ("customers".equals(path[1])) {
                    handleCustomersRequest(exchange, path);
                } else if ("items".equals(path[1])) {
                    handleItemsRequest(exchange, path);
                } else {
                    responseHandler.sendResponse(exchange, 404, "Not Found");
                }
                
            } else {
                responseHandler.sendResponse(exchange, 405, "Method Not Allowed");
            }
        } catch (Exception e) {
            responseHandler.sendResponse(exchange, 500, "Internal Server Error");
            e.printStackTrace();
        }
    }

    private void handleCustomersRequest(HttpExchange exchange, String[] path) throws IOException {
        JSONObject jsonCustomer = null;
        try {
            jsonCustomer = customersReqHandler.getCustomers(path);
            if (jsonCustomer != null) {
                responseHandler.getResponse(exchange, jsonCustomer.toString(), path, "customer", 200);
            } else {
                responseHandler.sendResponse(exchange, 404, "Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            responseHandler.sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void handleItemsRequest(HttpExchange exchange, String[] path) throws IOException{
        JSONObject jsonItem = null;
        try {
            jsonItem = itemsReqHandler.getItems(path);
            if (jsonItem != null) {
                responseHandler.getResponse(exchange, jsonItem.toString(), path, "items", 200);
            }else{
                responseHandler.sendResponse(exchange, 500, "Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            responseHandler.sendResponse(exchange, 500, "Internal Server Error");
        }
    }
}
