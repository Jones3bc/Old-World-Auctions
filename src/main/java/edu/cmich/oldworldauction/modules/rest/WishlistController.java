package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.WishlistDao;
import edu.cmich.oldworldauction.modules.models.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@Controller
public class WishlistController {
    @Autowired
    WishlistDao wishlistDao;

    @PostMapping("/save-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveItem(@ModelAttribute WishlistItem item) {
        this.wishlistDao.saveItem(item);
    }

    @PostMapping("/delete-wishlist-item")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteItem(@ModelAttribute WishlistItem item) {
        this.wishlistDao.deleteItem(item);
    }

    @GetMapping("/retrieve-wishlist-item")
    public WishlistItem getWishlistItem(@RequestParam String itemId, @RequestParam String userId, @RequestParam String reason) {
        return this.wishlistDao.retrieveItem(itemId, userId, reason);
    }

    @GetMapping("/retrieve-all-wishlist-items")
    public List<WishlistItem> getAllWishlistItems() {
        return this.wishlistDao.retrieveAllItems();
    }
}
