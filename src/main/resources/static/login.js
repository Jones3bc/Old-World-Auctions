"use strict";

document.addEventListener("DOMContentLoaded", function() {
    fetch("/loggedUser")
        .then(response => response.json())
        .then(data => {
        displayUser(data);
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
});

function displayUser(user){
    const userDiv = document.getElementById("user");
    userDiv.innerHTML = "";
    if(user.username != "") {
        userDiv.innerHTML = `
            <button class="dropbtn">Welcome, ` + user.username + `</button>
            <div class="dropdown-content">
            <a href="/account-page">Manage Account</a>
            <a href="/manage-items">Manage Items</a>
            <a href="/logout">Log Out</a>
            </div>
        `;
    } else {
        let loginButton = document.createElement("a");
        loginButton.setAttribute("href", "/login-page");
        loginButton.textContent = "Login";
        userDiv.appendChild(loginButton);
    }
}
