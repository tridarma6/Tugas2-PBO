package com.subscription.httpserver;

import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Customers;
import com.subscription.model.Items;
import com.subscription.persistence.ItemsAccess;

public class ItemsReqHandler {

    ItemsAccess itemsAccess = new ItemsAccess();

    private Items itemsParseJSONData(JSONObject jsonObject) throws SQLException {
        Items items = new Items();
        items.setId(jsonObject.optInt("id"));
        items.setName(jsonObject.optString("name"));
        items.setPrice(jsonObject.optInt("price"));
        items.setType(jsonObject.optString("type"));
        items.setIs_active(jsonObject.optInt("is_active"));
        return items;
    }

    public String postItems(JSONObject jsonObject) throws SQLException {
        Items item = itemsParseJSONData(jsonObject);
        return itemsAccess.addNewItem(item);
    }

    public String putItems(JSONObject jsonObject, String[] path) throws SQLException {
        Items item = itemsParseJSONData(jsonObject);
        int idItem = Integer.parseInt(path[2]);
        return itemsAccess.updateItem(idItem, item);
    }

    public String deleteItems(String[] path) throws SQLException {
        int idItem = Integer.parseInt(path[2]);
        return itemsAccess.deleteItem(idItem);
    }
    
    public JSONObject getItems(String path[]) throws SQLException {
        int idItem = 0;
        JSONObject jsonItem = null;

        if (path.length == 2) {
            if ("items".equals(path[1])) {
                jsonItem = new JSONObject();
                JSONArray jsonItemArray = new JSONArray();
                ArrayList<Items> listItems = itemsAccess.getAllItems();
                for (Items item : listItems) {
                    JSONObject jsonItemResult = new JSONObject();
                    jsonItemResult.put("id", item.getId());
                    jsonItemResult.put("name", item.getName());
                    jsonItemResult.put("price", item.getPrice());
                    jsonItemResult.put("type", item.getType());
                    jsonItemResult.put("is_active", item.getIs_active());
                    jsonItemArray.put(jsonItemResult);
                }
                jsonItem.put("Item Record", jsonItemArray);
            }
        } else if (path.length == 3) {
            jsonItem = new JSONObject();
            idItem = Integer.parseInt(path[2]);
            Items item = itemsAccess.getItemById(idItem);

            if (item != null && item.getId() != 0) {
                JSONObject jsonItemResult = new JSONObject();
                jsonItemResult.put("id", item.getId());
                jsonItemResult.put("name", item.getName());
                jsonItemResult.put("price", item.getPrice());
                jsonItemResult.put("type", item.getType());
                jsonItemResult.put("is_active", item.getIs_active());

                jsonItem.put("Item Record", jsonItemResult);
            } else {
                jsonItem.put("error", "Item not found");
            }
        } else if (path.length == 2 && "items?is_active=true".equals(path[1])) {
            jsonItem = new JSONObject();
            JSONArray jsonItemArray = new JSONArray();
            ArrayList<Items> listItems = itemsAccess.getItemsByIsActive();
            for (Items item : listItems) {
                JSONObject jsonItemResult = new JSONObject();
                jsonItemResult.put("id", item.getId());
                jsonItemResult.put("name", item.getName());
                jsonItemResult.put("price", item.getPrice());
                jsonItemResult.put("type", item.getType());
                jsonItemResult.put("is_active", item.getIs_active());
                jsonItemArray.put(jsonItemResult);
            }
            jsonItem.put("Item Record", jsonItemArray);
        }

        return jsonItem;
    }

    
}
