"use strict";

document.addEventListener("DOMContentLoaded", function() {
    let registrationForm = document.getElementById("registrationForm");

    registrationForm.addEventListener("submit", function(evt) {
        let valid = true;
        let errorP = document.getElementById("error");
        errorP.innerHTML = "";

        let username = document.getElementById("username");
        if(!username.value.match(new RegExp("^[a-zA-Z0-9]{8,}$"))) {
            username.style.borderColor = "red";
            errorP.innerHTML = errorP.innerHTML + "<br>* Username can only have letters and numbers and must be at least 8 characters long.<br>";
            valid = false;
        } else {
            username.style.borderColor = "black";
        }

        let password = document.getElementById("password");
        if(!password.value.match(new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"))) {
            password.style.borderColor = "red";
            errorP.innerHTML = errorP.innerHTML + "<br>* Password must be at least 8 characters long and contain at least 1 uppercase letter, 1 lowercase letter, and 1 number. <br>";
            valid = false;
        } else {
            password.style.borderColor = "black";
        }

        let confirmPassword = document.getElementById("confirmPassword");
        if(confirmPassword.value != password.value) {
            confirmPassword.style.borderColor = "red";
            errorP.innerHTML = errorP.innerHTML + "<br>* Passwords must match.";
            valid = false;
        } else {
            confirmPassword.style.borderColor = "black";
        }
        console.log(valid);
        if(!valid) {
            evt.preventDefault();
        }
    });
});
