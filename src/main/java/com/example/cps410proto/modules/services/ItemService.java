package com.example.cps410proto.modules.services;

import com.example.cps410proto.modules.models.AuctionItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/*
 *  Service class works with the auction item class to help in interacting with the user by adding and deleting items.
 *  The user interaction is defined in ItemController class
 */
@Service
public class ItemService {
    private Map<String, AuctionItem> auctionItemMap;

    public ItemService() {
        auctionItemMap = new HashMap<>();
    }

    public AuctionItem addItem(AuctionItem item) {
        auctionItemMap.put(item.getName(), item);
        return item;
    }
    public AuctionItem removeItem(AuctionItem item) {
        auctionItemMap.remove(item.getName(), item);
        return item;
    }

    public AuctionItem getItemByName(String itemName) {
        return auctionItemMap.get(itemName);
    }

    public Map<String, AuctionItem> getAllItems() {
        return auctionItemMap;
    }
}
