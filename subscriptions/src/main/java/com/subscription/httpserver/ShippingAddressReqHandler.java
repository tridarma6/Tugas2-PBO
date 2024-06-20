package com.subscription.httpserver;

import java.sql.SQLException;
import org.json.JSONObject;
import com.subscription.model.Customers;
import com.subscription.model.ShippingAddress;
import com.subscription.persistence.ShippingAddressAccess;

public class ShippingAddressReqHandler {

    ShippingAddressAccess shippingAddressAccess = new ShippingAddressAccess();

    private ShippingAddress parseShippingAddressFromJSON(JSONObject jsonObject) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(jsonObject.optInt("id"));
        // Ambil ID pelanggan dari JSON dan buat objek Customers
        shippingAddress.setCustomer(jsonObject.optInt("customer"));
        shippingAddress.setTitle(jsonObject.optString("title"));
        shippingAddress.setLine1(jsonObject.optString("line1"));
        shippingAddress.setLine2(jsonObject.optString("line2"));
        shippingAddress.setCity(jsonObject.optString("city"));
        shippingAddress.setProvince(jsonObject.optString("province"));
        shippingAddress.setPostcode(jsonObject.optString("postcode"));
        return shippingAddress;
    }

    public String putShippingAddress(JSONObject jsonObject, String[] path) throws SQLException {
        ShippingAddress shippingAddress = parseShippingAddressFromJSON(jsonObject);
        int idShippingAddress = Integer.parseInt(path[4]);
        int idCustomer = Integer.parseInt(path[2]);
        return shippingAddressAccess.updateShippingAddress(idCustomer, idShippingAddress, shippingAddress);
    }
}
