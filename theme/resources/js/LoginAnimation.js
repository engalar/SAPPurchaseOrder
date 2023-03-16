var inputs = document.getElementsByClassName("form-control");

for (var i = 0; i < inputs.length; i++) {
    inputs[i].addEventListener('keyup', check, false);
}

function check() {
    var checkInput = this;

    if (checkInput.value) {
        checkInput.closest(".login-textbox").classList.add("has-content");
    } else {
        checkInput.closest(".login-textbox").classList.remove("has-content");
    }
}

var emailText = document.getElementsByClassName("email-textbox")[0].getElementsByClassName("form-control")[0];
var passwordText = document.getElementsByClassName("password-textbox")[0].getElementsByClassName("form-control")[0];

//check if textboxes are empty
function EmptyValidation() {
    if (emailText.value.length == 0 || passwordText.value.length == 0) {
        return false;
    }
    else {
        return true;
    }
}

//on enter submit button
passwordText.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        if (EmptyValidation()) {
            document.getElementsByClassName("btn-signin")[0].click();
        }
    }
});

//on enter submit button
emailText.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        if (EmptyValidation()) {
            document.getElementsByClassName("btn-signin")[0].click();
        }
    }
});