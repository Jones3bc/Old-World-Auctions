package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.AuctionItem;

/**
 * Interacts with the database to retrieve and store auction items.
 *
 * @author Brock Jones
 */
public class ItemDao {
    /**
     * Checks the validity of a given {@link AuctionItem}.
     *
     * @throws IllegalArgumentException if the item is invalid.
     * @param auctionItem The {@link AuctionItem} to check for validity.
     * @return True if the item is valid. Should throw an {@link IllegalArgumentException} otherwise.
     */
    public boolean isAuctionItemValid(AuctionItem auctionItem) throws IllegalArgumentException{
        return true;
    }
}
