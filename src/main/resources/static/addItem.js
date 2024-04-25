"use strict";

document.addEventListener("DOMContentLoaded", function() {
    let itemForm = document.getElementById("itemAddForm");

    itemForm.addEventListener("submit", function(evt) {
        let valid = true;

        let name = document.getElementById("name");
        if(!name.value.match(new RegExp("^[a-zA-Z0-9\\s]+$"))) {
            name.style.borderColor = "red";
            let sibling = name.nextElementSibling;
            sibling.innerHTML = "Item name must have only letters and numbers";
            valid = false;
        } else {
            name.style.borderColor = "black";
            let sibling = name.nextElementSibling;
            sibling.innerHTML = "*";
        }

        let description = document.getElementById("description");
        if(!description.value.match(new RegExp("^[a-zA-Z0-9\\.\\s,!?\\(\\)]+$"))) {
            description.style.borderColor = "red";
            let sibling = description.nextElementSibling;
            sibling.innerHTML = "Item description must have only letters, numbers, and punctuation (. , ? !)";
            valid = false;
        } else {
            description.style.borderColor = "black";
            let sibling = description.nextElementSibling;
            sibling.innerHTML = "*";
        }

        let currentBid = document.getElementById("currentBid");
        if(!currentBid.value.match(new RegExp("^[0-9]{1,5}(\\.[0-9]{2})?$"))) {
            currentBid.style.borderColor = "red";
            let sibling = currentBid.nextElementSibling;
            sibling.innerHTML = "Bid amount must be in the format ##.## or ##";
            valid = false;
        } else {
            currentBid.style.borderColor = "black";
            let sibling = currentBid.nextElementSibling;
            sibling.innerHTML = "*";
        }

        let currentDate = new Date();

        let auctionStartTime = document.getElementById("auctionStartTime");
        let auctionStartTimeValue = new Date(auctionStartTime.value);
        let difference = auctionStartTimeValue - currentDate;
        if(difference < 1 * 60000) {
            auctionStartTime.style.borderColor = "red";
            let sibling = auctionStartTime.nextElementSibling;
            sibling.innerHTML = "Start time must be at least 60 seconds in the future";
            valid = false;
        } else {
            auctionStartTime.style.borderColor = "black";
            let sibling = auctionStartTime.nextElementSibling;
            sibling.innerHTML = "*";
        }

        let auctionEndTime = document.getElementById("auctionEndTime");
        let auctionEndTimeValue = new Date(auctionEndTime.value);
        difference = auctionEndTimeValue - currentDate;
        if(difference < 15 * 60000) {
            auctionEndTime.style.borderColor = "red";
            let sibling = auctionEndTime.nextElementSibling;
            sibling.innerHTML = "End time must be at least 15 minutes in the future";
            valid = false;
        } else {
            auctionEndTime.style.borderColor = "black";
            let sibling = auctionEndTime.nextElementSibling;
            sibling.innerHTML = "*";
        }

        difference = auctionEndTimeValue - auctionStartTimeValue;
        if(difference < 10 * 60000) {
            auctionEndTime.style.borderColor = "red";
            let sibling = auctionEndTime.nextElementSibling;
            sibling.innerHTML = "End time must be at least 10 minutes after the start time";
            valid = false;
        } else {
            let sibling = auctionEndTime.nextElementSibling;
            if(sibling.innerHTML != "End time must be at least 15 minutes in the future") {
                auctionEndTime.style.borderColor = "black";
                sibling.innerHTML = "*";
            }
        }

        if(!valid) {
            evt.preventDefault();
        }
    });
});