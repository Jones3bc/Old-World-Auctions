package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.WishlistDao;
import edu.cmich.oldworldauction.modules.models.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Handles requests and responses having to do with {@link WishlistItem}s.
 */
@RequestMapping("/")
@Controller
public class WishlistController {
    @Autowired
    WishlistDao wishlistDao;

    /**
     * Saves a wishlist item for a user.
     *
     * @param itemId The item ID for the wishlist item
     * @param userId The user ID for the user who is saving the item
     * @param currentBid The current bid at the time this item is saved
     * @param reason The reason for saving this item
     */
    @PostMapping("/save-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam BigDecimal currentBid, @RequestParam String reason) {
        this.wishlistDao.saveItem(new WishlistItem(itemId, userId, currentBid, reason));
    }

    /**
     * Deletes a wishlist item for a user.
     *
     * @param itemId The item ID of the item to delete
     * @param userId The user ID of the item to delete
     * @param currentBid The current bid associated with the item to delete
     * @param reason The reason why this item was originally saved
     */
    @PostMapping("/delete-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam BigDecimal currentBid, @RequestParam String reason) {
        this.wishlistDao.deleteItem(new WishlistItem(itemId, userId, currentBid, reason));
    }

    /**
     * Retrieves a single wishlist item for a user.
     *
     * @param itemId The ID of the wishlist item to retrieve
     * @param userId The user ID of the user who stored this item
     * @param reason The reason why this item was stored
     * @return
     */
    @GetMapping("/retrieve-wishlist-item")
    @ResponseBody
    public WishlistItem getWishlistItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam String reason) {
        return this.wishlistDao.retrieveItem(itemId, userId, reason);
    }

    /**
     * Retrieves all wishlist items that are in the wishlist DB table.
     *
     * @return A {@link List} of all {@link WishlistItem}s in the DB table
     */
    @GetMapping("/retrieve-all-wishlist-items")
    @ResponseBody
    public List<WishlistItem> getAllWishlistItems() {
        return this.wishlistDao.retrieveAllItems();
    }

    /**
     * Retrieves all wishlist items associated with a given user.
     *
     * @param userId The ID of the user to retrieve items for
     * @return A {@link List} of all {@link WishlistItem}s retrieved
     */
    @GetMapping("/retrieve-all-wishlist-items-for-user")
    @ResponseBody
    public List<WishlistItem> getAllWishlistItemsForUser(@RequestParam String userId) {
        return this.wishlistDao.retrieveAllItems(userId);
    }
}
