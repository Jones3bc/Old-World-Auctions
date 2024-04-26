package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.WishlistDao;
import edu.cmich.oldworldauction.modules.models.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/")
@Controller
public class WishlistController {
    @Autowired
    WishlistDao wishlistDao;

    @PostMapping("/save-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam BigDecimal currentBid, @RequestParam String reason) {
        this.wishlistDao.saveItem(new WishlistItem(itemId, userId, currentBid, reason));
    }

    @PostMapping("/delete-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam BigDecimal currentBid, @RequestParam String reason) {
        this.wishlistDao.deleteItem(new WishlistItem(itemId, userId, currentBid, reason));
    }

    @GetMapping("/retrieve-wishlist-item")
    @ResponseBody
    public WishlistItem getWishlistItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam String reason) {
        return this.wishlistDao.retrieveItem(itemId, userId, reason);
    }

    @GetMapping("/retrieve-all-wishlist-items")
    @ResponseBody
    public List<WishlistItem> getAllWishlistItems() {
        return this.wishlistDao.retrieveAllItems();
    }

    @GetMapping("/retrieve-all-wishlist-items-for-user")
    @ResponseBody
    public List<WishlistItem> getAllWishlistItemsForUser(@RequestParam String userId) {
        return this.wishlistDao.retrieveAllItems(userId);
    }
}
