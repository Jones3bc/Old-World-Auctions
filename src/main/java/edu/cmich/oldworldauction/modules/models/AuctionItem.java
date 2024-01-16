package edu.cmich.oldworldauction.modules.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an auction item.
 *
 * @author Brock Jones
 */
public class AuctionItem {
    private String name;
    private String description;
    private BigDecimal currentBid;
    private byte[] image;
    private String color;
    private int manufacturedYear;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private boolean auctionComplete;
    private String sellerUser;
    private String bidderUser;

    public AuctionItem(
            String name,
            String description,
            BigDecimal currentBid,
            byte[] image,
            String color,
            int manufacturedYear,
            LocalDateTime auctionStartTime,
            LocalDateTime auctionEndTime,
            String sellerUsername
    ) {
        this.name = name;
        this.description = description;
        this.currentBid = currentBid;
        this.image = image;
        this.color = color;
        this.manufacturedYear = manufacturedYear;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.auctionComplete = false;
        this.sellerUser = sellerUsername;
        this.bidderUser = null;
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
        return sellerUser;
    }

    public void setSellerId(String sellerId) {
        this.sellerUser = sellerId;
    }

    public String getBidderId() {
        return bidderUser;
    }

    public void setBidderId(String bidderId) {
        this.bidderUser = bidderId;
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
        return "AuctionItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentBid=" + currentBid +
                ", image=" + Arrays.toString(image) +
                ", color='" + color + '\'' +
                ", manufacturedYear=" + manufacturedYear +
                ", auctionStartTime=" + auctionStartTime +
                ", auctionEndTime=" + auctionEndTime +
                ", auctionComplete=" + auctionComplete +
                ", sellerUser='" + sellerUser + '\'' +
                ", bidderUser='" + bidderUser + '\'' +
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
