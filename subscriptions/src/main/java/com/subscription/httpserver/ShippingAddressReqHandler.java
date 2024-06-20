package com.subscription.httpserver;

import java.sql.SQLException;

import org.json.JSONObject;

import com.subscription.model.ShippingAddress;
import com.subscription.persistence.ShippingAddressAccess;

public class ShippingAddressReqHandler{

    ShippingAddressAccess shippingAddressAccess = new ShippingAddressAccess();

    private ShippingAddress parseShippingAddressFromJSON(JSONObject jsonObject) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(jsonObject.optInt("id"));
        shippingAddress.setCustomer(jsonObject.optString("customer"));
        shippingAddress.setTitle(jsonObject.optString("title"));
        shippingAddress.setLine1(jsonObject.optString("line1"));
        shippingAddress.setLine2(jsonObject.optString("line2"));
        shippingAddress.setCity(jsonObject.optString("city"));
        shippingAddress.setProvince(jsonObject.optString("province"));
        shippingAddress.setPostcode(jsonObject.optString("postcode"));
        return shippingAddress;
    }

    public String updateShippingAddress(JSONObject jsonRequestBody, String[] path) throws SQLException{
        try {
            // Mengambil customerId dan addressId dari path
            int customerId = Integer.parseInt(path[2]);
            int addressId = Integer.parseInt(path[4]);

            // Mengurai objek JSON menjadi ShippingAddress
            ShippingAddress shippingAddress = parseShippingAddressFromJSON(jsonRequestBody);

            // Memperbarui alamat pengiriman
            String response = shippingAddressAccess.updateShippingAddress(customerId, addressId, shippingAddress);

            // Mengembalikan respons sukses
            return "{\"status\":\"success\",\"message\":\"Alamat pengiriman berhasil diperbarui.\"}";

        } catch (NumberFormatException e) {
            // Menangani pengecualian format angka
            return "{\"status\":\"error\",\"message\":\"ID pelanggan atau ID alamat tidak valid.\"}";
        } catch (SQLException e) {
            // Menangani pengecualian SQL
            return "{\"status\":\"error\",\"message\":\"Kesalahan basis data: " + e.getMessage() + "\"}";
        } catch (Exception e) {
            // Menangani pengecualian umum
            return "{\"status\":\"error\",\"message\":\"Terjadi kesalahan yang tidak terduga: " + e.getMessage() + "\"}";
        }
    }
}