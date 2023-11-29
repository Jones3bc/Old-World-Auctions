//async function fetchAndDisplayItems() {
//    try {
//        const response = await fetch('/allItems');
//        const items = await response.json();
//
//        const gallery = document.querySelector('.Gallery');
//
//        items.forEach(item => {
//            const contentDiv = document.createElement('div');
//            contentDiv.className = 'content';
//
//            const itemName = document.createElement('h3');
//            itemName.textContent = item.getName();
//
//            contentDiv.appendChild(itemName);
//
//        });
//
//        const h3Elements = gallery.querySelectorAll('.content h3');
//
//        h3Elements.forEach(h3Element => {
//            console.log(h3Element.textContent);
//        });
//    } catch (error) {
//        console.error('Error fetching items:', error);
//    }
//}
//
//
//fetchAndDisplayItems();
alert("Inside Js");