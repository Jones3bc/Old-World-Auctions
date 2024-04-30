package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.ItemDao;
import edu.cmich.oldworldauction.modules.models.AuctionItemRetrieve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import edu.cmich.oldworldauction.modules.models.AuctionItemInsert;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Handles requests and responses having to do with {@link AuctionItemInsert}s.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@Controller
public class ItemController {

    @Autowired
    ItemDao itemDao;

    /**
     * Supplies the auctions-page page to the user in the browser.
     *
     * @return The auctionList HTML page.
     */
    @GetMapping("/auctions-page")
    public String auctionPage() {
        return "auctionList";
    }

    /**
     * Supplies the page to add an item to the user in the browser.
     *
     * @return The addItem HTML page.
     */
    @GetMapping("/addItem")
    public String addItem(){
        return "addItem";
    }

    /**
     * Displays an item page given the item's name. This page will describe the item and allow users to make bids.
     *
     * @param name The name of the item to display on this page
     * @param model A {@link Model} used to pass item information onto the item page
     * @return The getItem page for the given name
     */
    @GetMapping("/getItem")
    public String getItemByName(
            @RequestParam String name,
            Model model
    ) {
        try {
            AuctionItemRetrieve auctionItemRetrieve = this.itemDao.findItemByName(name);

            if (auctionItemRetrieve != null) {
                model.addAttribute("item", auctionItemRetrieve);
                return "getItem"; // Assuming "getItem" is the Thymeleaf template for displaying item details
            } else {
                throw new Exception("Item not found");
            }
        } catch (Exception e) {
            // Handle the exception (e.g., log it) and return an error page or redirect
            return "error"; // Assuming "error" is a Thymeleaf template for displaying errors
        }
    }

    /**
     * Displays an item page given the item's ID. This page will describe the item and allow users to make bids.
     *
     * @param itemId The ID of the item to display on this page
     * @param model A {@link Model} used to pass item information onto the item page
     * @return The getItem page for the given ID
     */
    @GetMapping("/getItemById")
    public String getItemById(
            @RequestParam String itemId,
            Model model
    ) {
        try {
            AuctionItemRetrieve auctionItemRetrieve = this.itemDao.findItemById(itemId);

            if (auctionItemRetrieve != null) {
                model.addAttribute("item", auctionItemRetrieve);
                return "getItem"; // Assuming "getItem" is the Thymeleaf template for displaying item details
            } else {
                throw new Exception("Item not found");
            }
        } catch (Exception e) {
            // Handle the exception (e.g., log it) and return an error page or redirect
            return "error"; // Assuming "error" is a Thymeleaf template for displaying errors
        }
    }

    /**
     * Returns all {@link AuctionItemInsert}s in the Old World Auctions DB.
     *
     * @return A {@link List} containing the {@link AuctionItemInsert}s.
     */
    @GetMapping("/allItemsJson")
    @ResponseBody
    public List<AuctionItemRetrieve> getAllItemsJson() {
        return this.itemDao.getAllItems();
    }

    /**
     * Retrieves all listed auction items for a given user.
     *
     * @param userId The ID of the user that has listed the items
     * @return A {@link List} of {@link AuctionItemRetrieve} that were listed by the given user
     */
    @GetMapping("/all-items-for-user")
    @ResponseBody
    public List<AuctionItemRetrieve> getItemsForSellerId(@RequestParam String userId) {
        return this.itemDao.getAllItems(userId);
    }

    /**
     * Displays the update item page to the user.
     *
     * @param itemId The item ID for the item that this update page is for
     * @param model A {@link Model} used to pass along the ID
     * @return The update item page to the user
     */
    @GetMapping("update-item-page")
    public String updateItemPage(@RequestParam String itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "updateItem";
    }

    /**
     * Retrieves an auction item by ID
     *
     * @param itemId The ID of the auction item to retrieve
     * @return The retrieved {@link AuctionItemRetrieve}
     */
    @GetMapping("get-item-by-id")
    @ResponseBody
    public AuctionItemRetrieve getItemByItemId(@RequestParam String itemId) {
        return this.itemDao.findItemById(itemId);
    }

    /**
     * Displays the manage items page to the user.
     *
     * @return The manage items page
     */
    @GetMapping("/manage-items")
    public String manageItems() {
        return "manageItems";
    }

    /**
     * Submits request to add an auction item.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to add.
     * @param model A {@link Model} used to transport data between HTML pages.
     * @return The confirmation HTML page.
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute AuctionItemInsert auctionItemInsert, Model model){
        model.addAllAttributes(auctionItemInsert.attributes());
        System.out.println(auctionItemInsert);
        this.itemDao.addAuctionItem(auctionItemInsert);
        return "confirmation";
    }

    /**
     * Allows the user to update an auction item.
     *
     * @param auctionItem The {@link AuctionItemRetrieve} that holds updated auction item information
     * @return The manage items page to the user
     */
    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute AuctionItemRetrieve auctionItem) {
        this.itemDao.updateItem(auctionItem);
        return "manageItems";
    }

    /**
     * Allows the user to update the bid amount for an auction item
     *
     * @param name The name of the auction item
     * @param bidderId The ID of the user who is bidding on the item
     * @param bidAmount The updated bid amount
     * @return The bid update confirmation page to the user
     */
    @PostMapping("/updateBid/{name}/{bidderId}/{bidAmount}")
    public String updateBid(@PathVariable String name, @PathVariable String bidderId, @PathVariable BigDecimal bidAmount) {

        this.itemDao.updateBid(name, bidderId, bidAmount);

        return "updateBid";
    }

    /**
     * Allows the user to delete an auction item.
     *
     * @param itemId The ID of the item to delete
     * @return The manage items page to the user
     */
    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam String itemId) {
        this.itemDao.deleteItem(itemId);
        return "manageItems";
    }
}

