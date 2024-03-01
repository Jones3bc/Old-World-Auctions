package edu.cmich.oldworldauction.modules.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AuctionItemRetrieve {
    private String itemID;
    private String name;
    private String category;
    private String description;
    private BigDecimal currentBid;
    private byte[] image;
    private String color;
    private int manufacturedYear;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private boolean auctionComplete;
    private String sellerID;
    private String bidderID;

    public AuctionItemRetrieve(
            String itemID,
            String name,
            String description,
            String category,
            BigDecimal currentBid,
            byte[] image,
            LocalDateTime auctionStartTime,
            LocalDateTime auctionEndTime,
            String sellerID,
            String bidderID
    ) {
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.description = description;
        this.currentBid = currentBid;
        this.image = image;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.auctionComplete = false;
        this.sellerID = sellerID;
        this.bidderID = bidderID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getBidderID() {
        return bidderID;
    }

    public void setBidderID(String bidderID) {
        this.bidderID = bidderID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDateTime getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(LocalDateTime auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public LocalDateTime getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public boolean isAuctionComplete() {
        return auctionComplete;
    }

    public void setAuctionComplete(boolean auctionComplete) {
        this.auctionComplete = auctionComplete;
    }

    public String getSellerId() {
        return sellerID;
    }

    public void setSellerId(String sellerId) {
        this.sellerID = sellerId;
    }

    public String getBidderId() {
        return bidderID;
    }

    public void setBidderId(String bidderId) {
        this.bidderID = bidderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getManufacturedYear() {
        return manufacturedYear;
    }

    public void setManufacturedYear(int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }

    @Override
    public String toString() {
        return "AuctionItemRetrieve{" +
                "itemID='" + itemID + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", currentBid=" + currentBid +
                ", image=" + image +
                ", color='" + color + '\'' +
                ", manufacturedYear=" + manufacturedYear +
                ", auctionStartTime=" + auctionStartTime +
                ", auctionEndTime=" + auctionEndTime +
                ", auctionComplete=" + auctionComplete +
                ", sellerID='" + sellerID + '\'' +
                ", bidderID='" + bidderID + '\'' +
                '}';
    }

    public Map<String, ?> attributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", name);
        attributes.put("description", description);
        attributes.put("currentBid", currentBid.toString());
        attributes.put("color", color);
        attributes.put("manufacturedYear", String.valueOf(manufacturedYear));

        return attributes;
    }
}
