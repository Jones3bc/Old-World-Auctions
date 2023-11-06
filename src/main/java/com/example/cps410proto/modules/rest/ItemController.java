package com.example.cps410proto.modules.rest;

import com.example.cps410proto.modules.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.cps410proto.modules.models.AuctionItem;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Handles requests and responses having to do with auction items.
 * Supplies appropriate pages to the user and takes user input.
 *
 * @author Brock Jones
 */

@RequestMapping("/")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Supplies the auctions-page page to the user in the browser.
     * @return The auctionList HTML page.
     */
    @GetMapping("/auctions-page")
    public String auctionPage(){
        return "auctionList";
    }

    /**
     * Interacts with ItemService class to add item by the user
     */
    @PostMapping("/addItem")
    public AuctionItem addItem(@RequestBody AuctionItem item) {
        return itemService.addItem(item);
    }

    /**
     * Interacts with ItemService class to remove item by the user
     */
    @PostMapping("/removeItem")
    public AuctionItem removeItem(@RequestBody AuctionItem item) {
        return itemService.removeItem(item);
    }
    /**
     * Handles the user request to get Item by name
     * */
    @PostMapping("/getItemByName")
    public AuctionItem getItemByName(@PathVariable String itemName) {
        return itemService.getItemByName(itemName);

    }
    /**
     * Get the user request to show all items
     * */
    @PostMapping("/getItems")
    public Map<String, AuctionItem> getAllItems() {
        return itemService.getAllItems();
    }

}