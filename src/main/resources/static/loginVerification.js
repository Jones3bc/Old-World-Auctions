"use strict";

document.addEventListener("DOMContentLoaded", async function() {
    let loginForm = document.getElementById("loginForm");
    let username = document.getElementById("username");
    let password = document.getElementById("password");

    if(window.location.href == "/login-page-erred") {
        username.style.borderColor = "red";
        password.style.borderColor = "red";
    }

    loginForm.addEventListener("submit", async function(evt) {
        let valid = true;

        fetch("/check-login?username=" + username.value + "&password=" + password.value)
                .then(response => response.json())
                .then(data => {
                    if(data.isValid == "false") {
                        window.location.replace("/login-page-erred");
                    }
                }).catch(error => {
                    console.error('Error fetching data:', error);
                });
    });
});