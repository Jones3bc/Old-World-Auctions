"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const userInput = document.getElementById("checkPassword");
    userInput.addEventListener("click", checkPassword, false);

    const addPaymentMethodForm = document.getElementById("paymentMethodAddForm");
    addPaymentMethodForm.addEventListener("submit", function(evt) {
        isPaymentMethodValid(
            evt,
            document.getElementById("cardNumber"),
            document.getElementById("userId"),
            document.getElementById("expirationMonth"),
            document.getElementById("expirationYear"),
            document.getElementById("cvv"),
            true,
            null
        );
    });
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
                fetch("/retrieve-payment-methods?userId=" + dataTwo.userId)
                    .then(response => response.json())
                    .then(dataThree => {
                    paymentMethodDiv.innerHTML = `
                        <div id="helper" style="color: red; padding-bottom: 15px;">All fields are required.</div>
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
                        paymentMethodButton.innerHTML = "Save";
                        paymentMethodButton.setAttribute("type", "submit");
                        paymentMethodButton.className = "update";

                        let deleteButton = document.createElement("button");
                        deleteButton.innerHTML = "X";
                        deleteButton.setAttribute("type", "button");
                        deleteButton.className = "remove";
                        deleteButton.addEventListener("click", function() {
                           if(confirm("Are you sure you want to delete this payment method?")) {
                               fetch("/delete-payment-method?paymentMethodId=" + paymentMethod.paymentId, { method: "POST" });
                               alert("Payment method deleted.");
                               window.location.replace("/account-page");
                           }
                        });

                        paymentMethodParagraph.appendChild(paymentMethodId);
                        paymentMethodParagraph.appendChild(userIdField);
                        paymentMethodParagraph.appendChild(paymentMethodNumber);
                        paymentMethodParagraph.appendChild(paymentMethodExpMonth);
                        paymentMethodParagraph.appendChild(paymentMethodExpYear);
                        paymentMethodParagraph.appendChild(paymentMethodCvv);
                        paymentMethodParagraph.appendChild(paymentMethodIsCredit);
                        paymentMethodParagraph.appendChild(paymentMethodButton);
                        paymentMethodParagraph.appendChild(deleteButton);

                        paymentMethodForm.appendChild(paymentMethodParagraph);
                        paymentMethodForm.addEventListener("submit", function(evt) {
                         isPaymentMethodValid(
                             evt,
                             paymentMethodNumber,
                             userIdField,
                             paymentMethodExpMonth,
                             paymentMethodExpYear,
                             paymentMethodCvv,
                             false,
                             document.getElementById("helper")
                         );
                         });

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

function isPaymentMethodValid(
    evt,
    cardNumberField,
    userIdField,
    expirationMonthField,
    expirationYearField,
    cvvField,
    hasRequiredMarks,
    explanationField
){
    let valid = true;
    explanationField.innerHTML = "All fields are required.";

    if(!cardNumberField.value.match(new RegExp("^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$"))) {
        cardNumberField.style.borderColor = "red";
        if(hasRequiredMarks) {
            let sibling = cardNumberField.nextElementSibling;
            sibling.innerHTML = "Must be in the format ####-####-####-####";
        } else {
            explanationField.innerHTML = explanationField.innerHTML + "<br>Card number must be in the format ####-####-####-####";
        }

        valid = false;
    } else {
        cardNumberField.style.borderColor = "black";
        if(hasRequiredMarks) {
            let sibling = cardNumberField.nextElementSibling;
            sibling.innerHTML = "*";
        }
    }

    if(hasRequiredMarks) {
        fetch("/check-card-number?cardNumber=" + cardNumberField.value + "&userId=" +userIdField.value)
            .then(response => response.json())
            .then(results => {
                console.log(results.isValid);
                if(results.isValid == false) {
                    cardNumberField.style.borderColor = "red";
                    let sibling = cardNumberField.nextElementSibling;
                    sibling.innerHTML = "This card is already registered to this user.";
                    valid = false;
                } else {
                    let sibling = cardNumberField.nextElementSibling;
                    if(sibling.innerHTML == "This card is already registered to this user.") {
                        cardNumberField.style.borderColor = "black";
                        sibling.innerHTML = "*";
                    }
                }
            });
    }

    let date = new Date();
    let month = date.getMonth() + 1;
    let year = date.getFullYear() % 100;
    if(expirationMonthField.value < 0 || expirationMonthField.value > 12) {
        expirationMonthField.style.borderColor = "red";
        if(hasRequiredMarks) {
            let sibling = expirationMonthField.nextElementSibling;
            sibling.innerHTML = "Must be between 1 and 12";
        } else {
            explanationField.innerHTML = explanationField.innerHTML + "<br>Expiration month must be between 1 and 12";
        }

        valid = false;
    } else {
        expirationMonthField.style.borderColor = "black";
        if(hasRequiredMarks) {
            let sibling = expirationMonthField.nextElementSibling;
            sibling.innerHTML = "*";
        }
    }

    if(expirationYearField.value < year || expirationYearField.value > 99) {
        expirationYearField.style.borderColor = "red";
        if(hasRequiredMarks) {
            let sibling = expirationYearField.nextElementSibling;
            sibling.innerHTML = "Must be between " + year + " and 99";
        } else {
            explanationField.innerHTML = explanationField.innerHTML + "<br>Expiration year must be between " + year + " and 99";
        }

        valid = false;
    } else {
        expirationYearField.style.borderColor = "black";
        if(hasRequiredMarks) {
            let sibling = expirationYearField.nextElementSibling;
            sibling.innerHTML = "*";
        }
    }

    if(expirationMonthField.value < month && expirationYearField.value == year) {
        expirationMonthField.style.borderColor = "red";
        if(hasRequiredMarks) {
            let sibling = expirationMonthField.nextElementSibling;
            sibling.innerHTML = "This month has already passed within the current year.";
        } else {
            explanationField.innerHTML = explanationField.innerHTML + "<br>The expiration month has already passed within the current year.";
        }

        valid = false;
    } else {
        let sibling = expirationMonthField.nextElementSibling;
        if(hasRequiredMarks && sibling.innerHTML == "This month has already passed within the current year.") {
            expirationMonthField.style.borderColor = "black";
            sibling.innerHTML = "*";
        }
    }

    if(!cvvField.value.match(new RegExp("^[0-9]{3}$"))) {
        cvvField.style.borderColor = "red";
        if(hasRequiredMarks) {
            let sibling = cvvField.nextElementSibling;
            sibling.innerHTML = "Must be 3 digits between 000 and 999";
        } else {
            explanationField.innerHTML = explanationField.innerHTML + "<br>CVV must be 3 digits between 000 and 999";
        }

        valid = false;
    } else {
        cvvField.style.borderColor = "black";
        if(hasRequiredMarks) {
            let sibling = cvvField.nextElementSibling;
            sibling.innerHTML = "*";
        }
    }

    if(!valid) {
        evt.preventDefault();
    }
}