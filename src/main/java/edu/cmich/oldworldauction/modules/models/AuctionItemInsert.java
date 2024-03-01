package edu.cmich.oldworldauction.modules.models;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an auction item.
 *
 * @author Brock Jones
 */
public class AuctionItemInsert {
    private String name;
    private String category;
    private String description;
    private BigDecimal currentBid;
    private MultipartFile image;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private boolean auctionComplete;
    private String sellerID;
    private String bidderID;

    public AuctionItemInsert(
            String name,
            String category,
            String description,
            BigDecimal currentBid,
            MultipartFile image,
            LocalDateTime auctionStartTime,
            LocalDateTime auctionEndTime,
            String sellerID
    ) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.currentBid = currentBid;
        this.image = image;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.auctionComplete = false;
        this.sellerID = sellerID;
        this.bidderID = null;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
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

    @Override
    public String toString() {
        return "AuctionItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentBid=" + currentBid +
                ", image=" + image +
                ", auctionStartTime=" + auctionStartTime +
                ", auctionEndTime=" + auctionEndTime +
                ", auctionComplete=" + auctionComplete +
                ", sellerUser='" + sellerID + '\'' +
                ", bidderUser='" + bidderID + '\'' +
                '}';
    }

    public Map<String, ?> attributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", name);
        attributes.put("category", category);
        attributes.put("description", description);
        attributes.put("currentBid", currentBid.toString());

        return attributes;
    }

}
