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
        if(!description.value.match(new RegExp("^[a-zA-Z0-9\\.\\s,!?]+$"))) {
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

        let image = document.getElementById("image");
        let auctionStartTime = document.getElementById("auctionStartTime");
        let auctionEndTime = document.getElementById("auctionEndTime");

        if(!valid) {
            evt.preventDefault();
        }
    });
});