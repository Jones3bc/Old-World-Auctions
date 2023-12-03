package com.example.cps410proto.modules.rest;

import com.example.cps410proto.modules.data.ItemDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import com.example.cps410proto.modules.models.AuctionItem;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Handles requests and responses having to do with auction items.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@CrossOrigin(origins = "http://your-frontend-domain.com")
@Controller
public class ItemController {

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


    @GetMapping("/allItems")
    public String getAllItems() {
        ItemDao itemDao = new ItemDao();
        System.out.println(itemDao.getAllItems());
        return "auctionList";
    }
    @GetMapping("/allItemsJson")
    @ResponseBody
    public List<AuctionItem> getAllItemsJson() {
        ItemDao itemDao = new ItemDao();
        System.out.println(itemDao.getAllItems());
        return itemDao.getAllItems();
    }
    /**
     * Submits request to add an auction item.
     *
     * @param auctionItem The {@link AuctionItem} to add.
     * @param model A {@link Model} used to transport data between HTML pages.
     * @return The confirmation HTML page.
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute AuctionItem auctionItem, Model model){
        System.out.println(auctionItem);
        model.addAllAttributes(auctionItem.attributes());
        ItemDao itemDao = new ItemDao();
        itemDao.addAuctionitem(auctionItem);
        return "confirmation";
    }

    /**
     * @param auctionItem The {@link AuctionItem} to add.
     * @return The confirmation HTML page.
     */
    @GetMapping("/GetItem")
    public AuctionItem getItemByName(@ModelAttribute AuctionItem auctionItem) throws Exception {

        ItemDao itemDao = new ItemDao();
        if (itemDao.isAuctionItemValid(auctionItem)) {
            return auctionItem;
        } else {
            throw new Exception("Item not Found");
        }
    }

    /**
     * Updates the description of an auction item.
     *
     * @param auctionItem The {@link AuctionItem} to update.
     * @param description The new description.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/changeDescription")
    public String changeDescription(@ModelAttribute AuctionItem auctionItem, String description, Model model) {
        try {
            AuctionItem existingItem = this.getItemByName(auctionItem);

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
     * Updates the bidding of an auction item.
     *
     * @param auctionItem The {@link AuctionItem} to update.
     * @param newBid The new bid.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/makeBid")
    public String makeBid(@ModelAttribute AuctionItem auctionItem, BigDecimal newBid, Model model) {
        try {
            AuctionItem existingItem = this.getItemByName(auctionItem);

            // Check if the new bid is greater than the current bid
            if (!(newBid.compareTo(existingItem.getCurrentBid()) > 0)) {
                // Update the current bid
                existingItem.setCurrentBid(newBid);

                // Add the updated item to the model
                model.addAttribute("currentBid", existingItem);

                // Return the confirmation view
                return "confirmation";
            } else {
                throw new Exception("The new bid must be greater than the current bid");
            }
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update bid");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

    /**
     * Updates the manufacture year of an auction item.
     *
     * @param auctionItem The {@link AuctionItem} to update.
     * @param newManufacturedYear The new newManufacturedYear.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/changeManufacturedYear")
    public String changeManufacturedYear(@ModelAttribute AuctionItem auctionItem, int newManufacturedYear, Model model) {
        try {
            AuctionItem existingItem = this.getItemByName(auctionItem);

            // Update the manufactured year
            existingItem.setManufacturedYear(newManufacturedYear);

            // Add the updated item to the model
            model.addAttribute("manufacturedYear", existingItem);

            // Return the confirmation view
            return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update manufactured year");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

    /**
     * Updates the name of an auction item.
     *
     * @param auctionItem The {@link AuctionItem} to update.
     * @param newName The new name.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/changeName")
    public String changeName(@ModelAttribute AuctionItem auctionItem, String newName, Model model) {
        try {
            AuctionItem existingItem = this.getItemByName(auctionItem);

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
     * @param auctionItem The {@link AuctionItem} to update.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/completeAuction")
    public String completeAuction(@ModelAttribute AuctionItem auctionItem, Model model) {
        try {
           if(auctionItem.isAuctionComplete()){
               model.addAttribute("name", auctionItem);
               auctionItem.setAuctionComplete(true);
           }
           return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            model.addAttribute("error", "Failed to update name");
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }

   /**
     * Sets a new color.
     *
     * @param auctionItem The {@link AuctionItem} to update.
     * @param newColor The new color.
     * @param model       The Spring MVC model.
     * @return The confirmation HTML page or an error page if the update fails.
     */
    @PostMapping("/setColor")
    public String setColor(@ModelAttribute AuctionItem auctionItem, String newColor, Model model) {
        try {
            AuctionItem existingItem = this.getItemByName(auctionItem);

            // Update the color
            existingItem.setColor(newColor);

            // Add the updated item to the model
            model.addAttribute("color", existingItem);

            // Return the confirmation view
            return "confirmation";
        } catch (Exception e) {
            // Handle the exception appropriately
            return "error";  // You should have an "error" Thymeleaf template for displaying error messages.
        }
    }


}

