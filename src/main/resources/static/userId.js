/*
 * Retrieves and stored the logged in user's ID into a hidden form field. If there is no logged in user,
 * redirect the user to the login page. Makes sure users have to login before accessing some pages.
 */
"use strict";

document.addEventListener("DOMContentLoaded", function() {
    fetch("/loggedUserID")
        .then(response => response.json())
        .then(data => {
        holdUser(data);
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
});

function holdUser(user){
    if(user.userId === "") {
        window.location.href = "/login-page";
    } else {
        const userInput = document.getElementById("userId");
        userInput.value = user.userId;
    }
}