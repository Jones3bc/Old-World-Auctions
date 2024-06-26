/*
 * Populates the manage items page with the user's listed items, their saved items, and their previous bid items.
 * Allows users to navigate to the item update page for their listed items.
 * Allows users to navigate to the item page for their saved and previous bid items.
 */
"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const userListedItems = document.getElementById("userListedItems");
    displayUserListedItems(userListedItems);

    const userSavedItems = document.getElementById("savedItems");
    const userBidItems = document.getElementById("previousBids");
    displaySavedItems(userSavedItems, userBidItems);
});

function displaySavedItems(savedItems, bidItems) {
    fetch("/loggedUserID")
            .then(response => response.json())
            .then(data => {
        fetch("/retrieve-all-wishlist-items-for-user?userId=" + data.userId)
                .then(response => response.json())
                .then(dataTwo => {
                    dataTwo.forEach(wishlistItem => {
                        fetch("/get-item-by-id?itemId=" + wishlistItem.itemId)
                            .then(response => response.json())
                            .then(item => {

                                let itemDiv = document.createElement("div");
                                itemDiv.className = "item";

                                let itemName = document.createElement("span");
                                itemName.innerHTML = "Name: " + item.name;
                                itemName.className = "clickable";

                                let currentBid = document.createElement("span");
                                currentBid.innerHTML = "Current bid: $" + item.currentBid.toFixed(2);
                                currentBid.className = "clickable";

                                let endTime = document.createElement("span");
                                if(new Date(item.auctionEndTime) - new Date() < 0) {
                                    endTime.innerHTML = "End Time: Ended";
                                } else {
                                    endTime.innerHTML = "End Time: " + item.auctionEndTime.split("T")[0] + " " + item.auctionEndTime.split("T")[1];
                                }
                                endTime.className = "clickable";

                                itemName.addEventListener("click", function() {
                                    window.location.href = "/getItemById?itemId=" + item.itemID;
                                });

                                currentBid.addEventListener("click", function() {
                                    window.location.href = "/getItemById?itemId=" + item.itemID;
                                });

                                endTime.addEventListener("click", function() {
                                    window.location.href = "/getItemById?itemId=" + item.itemID;
                                });

                                itemDiv.appendChild(itemName);
                                itemDiv.appendChild(currentBid);
                                itemDiv.appendChild(endTime);

                                let deleteSavedItemButton = document.createElement("button");
                                deleteSavedItemButton.className = "submit clickable";
                                deleteSavedItemButton.style.backgroundColor = "red";
                                deleteSavedItemButton.innerHTML = "X";
                                deleteSavedItemButton.addEventListener("click", function () {
                                   fetch("/delete-wishlist-item?itemId=" + wishlistItem.itemId + "&userId=" + wishlistItem.userId + "&currentBid=" + wishlistItem.currentBid + "&reason=" + wishlistItem.reason, { method: "POST" });
                                   window.location.replace("/manage-items");
                                });

                                itemDiv.appendChild(deleteSavedItemButton);


                                if(wishlistItem.reason == "SAVED") {
                                    savedItems.appendChild(itemDiv);
                                    savedItems.appendChild(document.createElement("br"));
                                } else {
                                    bidItems.appendChild(itemDiv);
                                    bidItems.appendChild(document.createElement("br"));
                                }

                            })
                            .catch(error => {
                                console.error('Error fetching items:', error);
                            });
                    })
                })
                .catch(error => {
                    console.error('Error fetching items:', error);
                });
            })
            .catch(error => {
                console.error('Error fetching items:', error);
            });
}

function displayUserListedItems(userListedItems) {
    fetch("/loggedUserID")
        .then(response => response.json())
        .then(data => {
        if(data.userId === "") {
            window.location.replace("/login-page");
        }
        fetch("/all-items-for-user?userId=" + data.userId)
                .then(response => response.json())
                .then(dataTwo => {
                    dataTwo.forEach(item => {
                        let itemDiv = document.createElement("div");
                        itemDiv.className = "item";

                        let itemName = document.createElement("span");
                        itemName.innerHTML = "Name: " + item.name;
                        itemName.className = "clickable";

                        let currentBid = document.createElement("span");
                        currentBid.innerHTML = "Current bid: $" + item.currentBid.toFixed(2);
                        currentBid.className = "clickable";

                        let endTime = document.createElement("span");
                        if(new Date(item.auctionEndTime) - new Date() < 0) {
                            endTime.innerHTML = "End Time: Ended";
                        } else {
                            endTime.innerHTML = "End Time: " + item.auctionEndTime.split("T")[0] + " " + item.auctionEndTime.split("T")[1];
                        }
                        endTime.className = "clickable";

                        itemDiv.appendChild(itemName);
                        itemDiv.appendChild(currentBid);
                        itemDiv.appendChild(endTime);

                        itemDiv.addEventListener("click", function() {
                            window.location.href = "/update-item-page?itemId=" + item.itemID
                        });

                        userListedItems.appendChild(itemDiv);
                        userListedItems.appendChild(document.createElement("br"));
                    });
                })
                .catch(error => {
                    console.error('Error fetching items:', error);
                });
        })
        .catch(error => {
            console.error('Error fetching items:', error);
        });
}