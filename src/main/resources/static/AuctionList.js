"https://code.jquery.com/jquery-3.6.4.min.js"
"https://js.stripe.com/v3/"

document.addEventListener("DOMContentLoaded", function() {

        fetch("/allItemsJson")
            .then(response => response.json())
            .then(data => {
                console.log('Retrieved items:', data);
                displayAuctionItems(data);
            })
            .catch(error => {
                console.error('Error fetching items:', error);
            });
    });

function displayAuctionItems(items) {
    const auctionListDiv = document.getElementById("gallery");
    auctionListDiv.innerHTML = ''; // Clear previous content

    // Group items by category
    const groupedItems = groupItemsByCategory(items);

    // Iterate through each category and display items
    for (const category in groupedItems) {
        const categoryHeader = document.createElement("h2");
        categoryHeader.textContent = category;
        auctionListDiv.appendChild(categoryHeader);

        const categoryItems = groupedItems[category];
        const categoryItemsDiv = document.createElement("div");
        categoryItemsDiv.classList.add("category-items");

        categoryItems.forEach(item => {
            const contentDiv = document.createElement("div");
            contentDiv.classList.add("content");

            if (item.image !== null) {
                // Convert Base64-encoded image data to an actual image
                const imageData = atob(item.image);
                const imageDataUrl = `data:image/jpeg;base64,${imageData}`;

                const imageElement = document.createElement("img");
                imageElement.src = imageDataUrl;
                contentDiv.appendChild(imageElement);
            } else {
                // Handle case where image data is null
                const noImageElement = document.createElement("p");
                noImageElement.textContent = "No image available";
                contentDiv.appendChild(noImageElement);
            }

            contentDiv.innerHTML += `
                <h3>${item.name}</h3>
                <p>$${item.currentBid}</p>
                <h6>${item.description}</h6>
                <button class="buy-1" onclick="buyNow('${item.name}')">Buy Now</button>
                <button class="save" onclick="saveItem('${item.name}')">Save</button>
            `;

            categoryItemsDiv.appendChild(contentDiv);
        });

        auctionListDiv.appendChild(categoryItemsDiv);
    }
}

function groupItemsByCategory(items) {
    const groupedItems = {};
    items.forEach(item => {
        if (!groupedItems[item.category]) {
            groupedItems[item.category] = [];
        }
        groupedItems[item.category].push(item);
    });
    return groupedItems;
}

function buyNow(itemName) {
    const url = `/getItem?name=${encodeURIComponent(itemName)}`;
    window.location.href = url;
}