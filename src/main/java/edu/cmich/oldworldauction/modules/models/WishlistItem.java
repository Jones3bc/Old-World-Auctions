package edu.cmich.oldworldauction.modules.models;

import java.math.BigDecimal;

public record WishlistItem(
        String itemId, //The itemId of the wishlist item
        String userId, //The ID of the user that is saving the item
        BigDecimal currentBid, //The bid amount at the time of save
        String reason //The reason for saving the item - SAVED, PREVIOUS_BID
) {
    //No code necessary
}
