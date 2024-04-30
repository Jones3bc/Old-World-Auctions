/*
 * Populates the login/user account directory in the top right corner of most website pages.
 * If the user is logged in, display the "welcome, {user}" dropdown that the user can use to navigate to the
 * manage account page and manage items page. This dropdown also allows the user to log out.
 * If there is no logged in user, display a button/link that allows users to navigate to the login page.
 */
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
