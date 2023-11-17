package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.AuctionItem;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interacts with the database to retrieve and store auction items.
 * Also checks validity of {@link AuctionItem}s.
 */
public class ItemDao {
    /**
     * Checks the validity of a given {@link AuctionItem}.
     *
     * @param auctionItem The {@link AuctionItem} to check for validity.
     * @return True if the item is valid. Should throw an {@link IllegalArgumentException} otherwise.
     * @throws IllegalArgumentException if the item is invalid.
     */
    public boolean isAuctionItemValid(AuctionItem auctionItem) throws IllegalArgumentException {
        BigDecimal currentBid = auctionItem.getCurrentBid();
        int manufacturedYear = auctionItem.getManufacturedYear();

        if (StringUtils.isEmptyOrWhitespace(auctionItem.getName())
                || StringUtils.isEmptyOrWhitespace(auctionItem.getDescription())
                || StringUtils.isEmptyOrWhitespace(auctionItem.getColor())) {
            throw new IllegalArgumentException("Name, Description, and Color fields must be non-null and not be only whitespace.");
        } else if (currentBid == null || currentBid.compareTo(BigDecimal.ZERO) < 0 || currentBid.scale() != 2) {
            throw new IllegalArgumentException("Current Bid field is invalid. Must be non-null, > 0 and have 2 decimal place values.");
        } else if (manufacturedYear < 0 || manufacturedYear > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Manufactured year field must be > 0 and less than the current year.");
        } else if (auctionItem.getAuctionStartTime() == null || auctionItem.getAuctionEndTime() == null) {
            throw new IllegalArgumentException("Auction start/end times cannot be null");
        }

        return true;
    }
}
