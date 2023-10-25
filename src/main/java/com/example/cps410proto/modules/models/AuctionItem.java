package com.example.cps410proto.modules.models;

import java.math.BigDecimal;
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
    private String color;
    private int manufacturedYear;

    public AuctionItem(String name, String description, BigDecimal currentBid, String color, int manufacturedYear) {
        this.name = name;
        this.description = description;
        this.currentBid = currentBid;
        this.color = color;
        this.manufacturedYear = manufacturedYear;
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
                ", color='" + color + '\'' +
                ", manufacturedYear=" + manufacturedYear +
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
