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
     * Retrieves an {@link AuctionItemInsert} given its name.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to add.
     * @return The confirmation HTML page.
     */
    @GetMapping("/GetItem")
    public AuctionItemInsert getItemByName(AuctionItemInsert auctionItemInsert) throws Exception {
        if (this.itemDao.isAuctionItemValid(auctionItemInsert)) {
            return auctionItemInsert;
        } else {
            throw new Exception("Item not Found");
        }
    }

    /**
     * Updates the description of an auction item.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to update.
     * @param description The new description.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/changeDescription")
    public String changeDescription(@ModelAttribute AuctionItemInsert auctionItemInsert, String description, Model model) {
        try {
            AuctionItemInsert existingItem = this.getItemByName(auctionItemInsert);

            // Update the description
            existingItem.setDescription(description);

            // Add the updated item to the model
            model.addAttribute("description", existingItem);

            // Return the confirmation view
            return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update description");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

    /**
     * Updates the name of an auction item.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to update.
     * @param newName The new name.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/changeName")
    public String changeName(@ModelAttribute AuctionItemInsert auctionItemInsert, String newName, Model model) {
        try {
            AuctionItemInsert existingItem = this.getItemByName(auctionItemInsert);

            // Update the name
            existingItem.setName(newName);

            // Add the updated item to the model
            model.addAttribute("auctionItem", existingItem);

            // Return the confirmation view
            return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update name");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

    /**
     * Completes the auction of the item.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to update.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/completeAuction")
    public String completeAuction(@ModelAttribute AuctionItemInsert auctionItemInsert, Model model) {
        try {
           if(auctionItemInsert.isAuctionComplete()){
               model.addAttribute("name", auctionItemInsert);
               auctionItemInsert.setAuctionComplete(true);
           }
           return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update name");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

    @PostMapping("/updateItem/{originalName}")
    public void updateItem(@ModelAttribute AuctionItemInsert auctionItemInsert, @PathVariable String originalName) {
        this.itemDao.updateItem(auctionItemInsert, originalName);
    }

    @PostMapping("/updateBid/{name}/{bidderId}/{bidAmount}")
    public String updateBid(@PathVariable String name, @PathVariable String bidderId, @PathVariable BigDecimal bidAmount) {

        this.itemDao.updateBid(name, bidderId, bidAmount);

        return "updateBid";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam String name) {
        this.itemDao.deleteItem(name);
        return "index";
    }
}

