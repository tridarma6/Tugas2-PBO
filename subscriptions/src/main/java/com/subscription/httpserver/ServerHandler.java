package com.subscription.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.subscription.model.ShippingAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerHandler implements HttpHandler {
    String response;
    ShippingAddressReqHandler shippingAddressReqHandler = new ShippingAddressReqHandler();
    ResponseHandler responseHandler = new ResponseHandler();
    CustomersReqHandler customersReqHandler = new CustomersReqHandler();
    ItemsReqHandler itemsReqHandler = new ItemsReqHandler();
    SubscriptionsReqHandler subscriptionsReqHandler = new SubscriptionsReqHandler();
    CardsReqHandler cardsReqHandler = new CardsReqHandler();
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                if (path.length == 0 || (path.length == 1 && path[0].isEmpty())) {
                    responseHandler.sendResponse(exchange, 200, "Hei!");
                } else if ("customers".equals(path[1])) {
                    handleCustomersRequest(exchange, path);
                } else if ("items".equals(path[1])) {
                    handleItemsRequest(exchange, path);
                } else if ("subscriptions".equals(path[1])) {
                    handleSubscripitonsRequest(exchange, path);
                } else {
                    responseHandler.sendResponse(exchange, 404, "Not Found p");
                }
            
            }
            else if ("POST".equals(exchange.getRequestMethod())) {
                if ("customers".equals(path[1])) {
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = customersReqHandler.postCustomer(jsonRequestBody);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else if("items".equals(path[1])){
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = itemsReqHandler.postItems(jsonRequestBody);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }else if ("subscriptions".equals(path[1])) {
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = subscriptionsReqHandler.postSubscriptions(jsonRequestBody);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        // Print stack trace for detailed error analysis
                        e.printStackTrace();
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format: " + e.getMessage());
                    } catch (SQLException e) {
                        // Log SQL exception
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

                

            }else if ("PUT".equals(exchange.getRequestMethod())) {
                if ("customers".equals(path[1])) {
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = customersReqHandler.putCustomer(jsonRequestBody, path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        // Handle invalid JSON format
                        System.err.println("Invalid JSON format: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        // Handle SQL exceptions
                        System.err.println("SQL Exception: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 500, "Internal Server Error");
                    }
                }else if("items".equals(path[1])){
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = itemsReqHandler.putItems(jsonRequestBody, path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        // Handle invalid JSON format
                        System.err.println("Invalid JSON format: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        // Handle SQL exceptions
                        System.err.println("SQL Exception: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 500, "Internal Server Error");
                    }
                }else if("shipping_address".equals(path[3])){
                    String requestBodyString = parseRequestBody(exchange.getRequestBody());
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    System.out.println("Request Body: " + requestBodyString); // Log the request body
                    try {
                        JSONObject jsonRequestBody = new JSONObject(requestBodyString);
                        response = shippingAddressReqHandler.putShippingAddress(jsonRequestBody, path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        // Handle invalid JSON format
                        System.err.println("Invalid JSON format: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        // Handle SQL exceptions
                        System.err.println("SQL Exception: " + e.getMessage());
                        responseHandler.sendResponse(exchange, 500, "Internal Server Error");
                    }
                }
            }else if("DELETE".equals(exchange.getRequestMethod())){
                if ("items".equals(path[1])) {
                    try {
                        response = itemsReqHandler.deleteItems(path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else if("cards".equals(path[3])){
                    try {
                        response = cardsReqHandler.deleteCards(path);
                        responseHandler.sendResponse(exchange, 200, response);
                    } catch (JSONException e) {
                        responseHandler.sendResponse(exchange, 400, "Invalid JSON format");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
             else {
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
    private void handleSubscripitonsRequest(HttpExchange exchange, String[] path) throws IOException {
        JSONObject jsonSubs = null;
        try {
            jsonSubs = subscriptionsReqHandler.getSubscriptions(path);
            if (jsonSubs != null) {
                responseHandler.getResponse(exchange, jsonSubs.toString(), path, "subscripitons", 200);
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

    private void handleUnsupportedMethod (HttpExchange exchange) throws IOException {
        response = "RequestHandler method tidak didukung/tidak ada.";
        responseHandler.sendResponse(exchange, 405, response);
    }

    private String parseRequestBody(InputStream requestBody) throws IOException {
        byte[] requestBodyBytes = requestBody.readAllBytes();
        return new String(requestBodyBytes, StandardCharsets.UTF_8);
    }
}
