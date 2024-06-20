package com.subscription.httpserver;

import java.sql.SQLException;

import org.json.JSONObject;

import com.subscription.model.Cards;
import com.subscription.persistence.CardsAccess;

public class CardsReqHandler{
    CardsAccess cardsAccess = new CardsAccess();

    private Cards parseCardsFromJSON(JSONObject jsonObject) {
        Cards cards = new Cards();
        cards.setId(jsonObject.optInt("id"));
        cards.setCustomer(jsonObject.optInt("customer"));
        cards.setCard_type(jsonObject.optString("card_type"));
        cards.setMasked_number(jsonObject.optString("masked_number"));
        cards.setExpiry_month(jsonObject.optInt("expiry_month"));
        cards.setExpiry_year(jsonObject.optInt("expiry_yeaar"));
        cards.setStatus(jsonObject.optString("status"));
        cards.setIs_primary(jsonObject.optInt("is_primary"));
        return cards;
    }

    public String deleteCards(String[] path) throws SQLException {
        // Parse customer ID and card ID from the path
        int customerId = Integer.parseInt(path[2]); // Assuming /cards/customerId/cardId
        int cardId = Integer.parseInt(path[4]);

        return cardsAccess.deleteCard(customerId, cardId);
    }
}