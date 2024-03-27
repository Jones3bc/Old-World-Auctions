"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const userInput = document.getElementById("checkPassword");
    userInput.addEventListener("click", checkPassword, false);
});

function checkPassword() {
    const passwordInput = document.getElementById("paymentMethodPassword");
    const password = passwordInput.value;

    fetch("/check-password?password=" + password)
        .then(response => response.json())
        .then(data => {
        console.log(data.isValid);
        if(data.isValid === "true") {
            const paymentMethodDiv = document.getElementById("paymentMethods");
            paymentMethodDiv.innerHTML = "";
            fetch("/loggedUserID")
                .then(response => response.json())
                .then(dataTwo => {
                fetch("/retrieve-payment-methods?userId=" + dataTwo.userId)
                    .then(response => response.json())
                    .then(dataThree => {
                    dataThree.forEach(paymentMethod => {
                        const paymentMethodForm = document.createElement("form");

                        const paymentMethodParagraph = document.createElement("p");

                        const paymentMethodNumber = document.createElement("input");
                        paymentMethodNumber.setAttribute("type", "text");

                        const paymentMethodExpMonth = document.createElement("input");
                        paymentMethodExpMonth.setAttribute("type", "number");

                        const paymentMethodExpYear = document.createElement("input");
                        paymentMethodExpYear.setAttribute("type", "number");

                        const paymentMethodCvv = document.createElement("input");
                        paymentMethodCvv.setAttribute("type", "number");

                        const paymentMethodIsCredit = document.createElement("select");

                        const paymentMethodYes = document.createElement("option");
                        paymentMethodYes.value = "Yes";
                        paymentMethodYes.innerHTML = "Yes";

                        const paymentMethodNo = document.createElement("option");
                        paymentMethodNo.value = "No";
                        paymentMethodNo.innerHTML = "No";

                        paymentMethodIsCredit.appendChild(paymentMethodYes);
                        paymentMethodIsCredit.appendChild(paymentMethodNo);

                        paymentMethodParagraph.appendChild(paymentMethodNumber);
                        paymentMethodParagraph.appendChild(paymentMethodExpMonth);
                        paymentMethodParagraph.appendChild(paymentMethodExpYear);
                        paymentMethodParagraph.appendChild(paymentMethodCvv);
                        paymentMethodParagraph.appendChild(paymentMethodIsCredit);

                        paymentMethodForm.appendChild(paymentMethodParagraph);

                        paymentMethodDiv.appendChild(paymentMethodForm);
                    });
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
        } else {
            passwordInput.style.borderColor = "red";
        }
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
}