"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const params = new Proxy(new URLSearchParams(window.location.search), {
      get: (searchParams, prop) => searchParams.get(prop),
    });

    fetch("/get-item-by-id?itemId=" + params.itemId)
            .then(response => response.json())
            .then(dataTwo => {
                if(document.getElementById("userId").value != dataTwo.sellerID) {
                    window.location.replace("/");
                }

                let itemId = document.getElementById("itemId");
                itemId.value = params.itemId;

                let name = document.getElementById("name");
                name.value = dataTwo.name;

                let category = document.getElementById("category");
                category.value = dataTwo.category;

                let description = document.getElementById("description");
                description.value = dataTwo.description;

                let currentBid = document.getElementById("currentBid");
                currentBid.value = dataTwo.currentBid.toFixed(2);

                let auctionStartTime = document.getElementById("auctionStartTime");
                auctionStartTime.value = dataTwo.auctionStartTime;

                let auctionEndTime = document.getElementById("auctionEndTime");
                auctionEndTime.value = dataTwo.auctionEndTime;

                let auctionCancelButton = document.getElementById("cancelAuction");
                if(new Date(auctionEndTime.value) - new Date() < 0) {
                    auctionCancelButton.style = "display: none";
                } else {
                    auctionCancelButton.addEventListener("click", function(){
                        if(confirm("Are you sure you want to cancel this auction?")) {
                            fetch("/deleteItem?itemId=" + params.itemId, { method: "POST" });
                            alert("Auction cancelled.");
                            window.location.replace("/manage-items");
                        }
                    });
                }

            })
            .catch(error => {
                console.error('Error fetching items:', error);
            });
});