"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const userListedItems = document.getElementById("userListedItems");
    displayUserListedItems(userListedItems);
});

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

                        let currentBid = document.createElement("span");
                        currentBid.innerHTML = "Current bid: $" + item.currentBid.toFixed(2);

                        let endTime = document.createElement("span");
                        if(new Date(item.auctionEndTime) - new Date() < 0) {
                            endTime.innerHTML = "End Time: Ended";
                        } else {
                            endTime.innerHTML = "End Time: " + item.auctionEndTime.split("T")[0] + " " + item.auctionEndTime.split("T")[1];
                        }

                        itemDiv.appendChild(itemName);
                        itemDiv.appendChild(currentBid);
                        itemDiv.appendChild(endTime);

                        itemDiv.addEventListener("click", function() {
                            window.location.replace("/update-item-page?itemId=" + item.itemID)
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