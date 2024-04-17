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
        if(data.isValid == "true") {
            const paymentMethodDiv = document.getElementById("paymentMethods");
            paymentMethodDiv.innerHTML = "";
            fetch("/loggedUserID")
                .then(response => response.json())
                .then(dataTwo => {
                console.log("Hello");
                fetch("/retrieve-payment-methods?userId=" + dataTwo.userId)
                    .then(response => response.json())
                    .then(dataThree => {
                    paymentMethodDiv.innerHTML = `
                        <form style="margin-left: 100px auto;">
                            <input value="Card Number" style="padding: 15px; outline: none;" disabled/>
                            <input value="Expiration Month" style="padding: 15px; outline: none;" disabled/>
                            <input value="Expiration Year" style="padding: 15px; outline: none;" disabled/>
                            <input value="CVV" style="padding: 15px; outline: none;" disabled/>
                            <select style="padding: 15px" disabled>
                                <option>Is this a credit card?</option>
                            </select>
                        </form>
                    `;
                    dataThree.forEach(paymentMethod => {
                        const paymentMethodForm = document.createElement("form");
                        paymentMethodForm.setAttribute("method", "post");
                        paymentMethodForm.setAttribute("action", "/update-payment-method");

                        const paymentMethodParagraph = document.createElement("p");

                        const paymentMethodId = document.createElement("input");
                        paymentMethodId.setAttribute("type", "hidden");
                        paymentMethodId.setAttribute("name", "paymentMethodId");
                        paymentMethodId.value = paymentMethod.paymentId;

                        const userIdField = document.createElement("input");
                        userIdField.setAttribute("type", "hidden");
                        userIdField.setAttribute("name", "userId");
                        userIdField.value = paymentMethod.userId;

                        const paymentMethodNumber = document.createElement("input");
                        paymentMethodNumber.setAttribute("type", "text");
                        paymentMethodNumber.setAttribute("name", "cardNumber")
                        paymentMethodNumber.value = paymentMethod.cardNumber;
                        paymentMethodNumber.style.padding = "15px";

                        const paymentMethodExpMonth = document.createElement("input");
                        paymentMethodExpMonth.setAttribute("type", "number");
                        paymentMethodExpMonth.setAttribute("name", "expirationMonth");
                        paymentMethodExpMonth.value = paymentMethod.expirationMonth;
                        paymentMethodExpMonth.style.padding = "15px";

                        const paymentMethodExpYear = document.createElement("input");
                        paymentMethodExpYear.setAttribute("type", "number");
                        paymentMethodExpYear.setAttribute("name", "expirationYear")
                        paymentMethodExpYear.value = paymentMethod.expirationYear;
                        paymentMethodExpYear.style.padding = "15px"

                        const paymentMethodCvv = document.createElement("input");
                        paymentMethodCvv.setAttribute("type", "number");
                        paymentMethodCvv.setAttribute("name", "cvv")
                        paymentMethodCvv.value = paymentMethod.cvv;
                        paymentMethodCvv.style.padding = "15px";

                        const paymentMethodIsCredit = document.createElement("select");
                        paymentMethodIsCredit.setAttribute("name", "credit");
                        paymentMethodIsCredit.style.padding = "15px";

                        const paymentMethodYes = document.createElement("option");
                        paymentMethodYes.value = "Yes";
                        paymentMethodYes.innerHTML = "Yes";

                        const paymentMethodNo = document.createElement("option");
                        paymentMethodNo.value = "No";
                        paymentMethodNo.innerHTML = "No";

                        paymentMethodIsCredit.appendChild(paymentMethodYes);
                        paymentMethodIsCredit.appendChild(paymentMethodNo);

                        if(paymentMethod.credit == true) {
                            paymentMethodIsCredit.value = "Yes";
                        } else {
                            paymentMethodIsCredit.value = "No";
                        }

                        const paymentMethodButton = document.createElement("button");
                        paymentMethodButton.innerHTML = "Update";
                        paymentMethodButton.setAttribute("type", "submit");
                        paymentMethodButton.className = "submit";

                        paymentMethodParagraph.appendChild(paymentMethodId);
                        paymentMethodParagraph.appendChild(userIdField);
                        paymentMethodParagraph.appendChild(paymentMethodNumber);
                        paymentMethodParagraph.appendChild(paymentMethodExpMonth);
                        paymentMethodParagraph.appendChild(paymentMethodExpYear);
                        paymentMethodParagraph.appendChild(paymentMethodCvv);
                        paymentMethodParagraph.appendChild(paymentMethodIsCredit);
                        paymentMethodParagraph.appendChild(paymentMethodButton);

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