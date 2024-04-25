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

                        let itemName = document.createElement("span");
                        itemName.innerHTML = item.name;

                        let currentBid = document.createElement("span");
                        currentBid.innerHTML = item.currentBid;

                        let endTime = document.createElement("span");
                        endTime.innerHTML = item.auctionEndTime;

                        itemDiv.appendChild(itemName);
                        itemDiv.appendChild(currentBid);
                        itemDiv.appendChild(endTime);

                        userListedItems.appendChild(itemDiv);
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