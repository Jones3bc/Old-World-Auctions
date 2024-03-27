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
        window.location.replace("/");
    } else {
        const userInput = document.getElementById("userId");
        userInput.value = user.userId;
        console.log(userInput.value);
    }
}