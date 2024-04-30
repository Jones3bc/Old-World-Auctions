package edu.cmich.oldworldauction.modules.models;

import java.math.BigDecimal;

/**
 * Represents a wishlist item that is stored in our database and displayed for and used by users of the website.
 *
 * @param itemId The ID of the wishlist item
 * @param userId The user ID of the user who saved the item
 * @param currentBid The current bid of the item at the time it was saved
 * @param reason The reason for saving the wishlist item
 */
public record WishlistItem(
        String itemId, //The itemId of the wishlist item
        String userId, //The ID of the user that is saving the item
        BigDecimal currentBid, //The bid amount at the time of save
        String reason //The reason for saving the item - SAVED, PREVIOUS_BID
) {
    //No code necessary
}
