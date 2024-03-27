"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const userInput = document.getElementById("checkPassword");
    userInput.addEventListener("click", checkPassword());
});

function checkPassword() {
    const passwordInput = document.getElementById("paymentMethodPassword");
    const password = passwordInput.value;

    fetch("/check-password?password=" + password)
        .then(response => response.json())
        .then(data => {
        if(data.isValid === "true") {
            const paymentMethodDiv = document.getElementById("paymentMethods");
            paymentMethodDiv.innerHTML = "";

        } else {
            passwordInput.style.borderColor = "red";
        }
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
}