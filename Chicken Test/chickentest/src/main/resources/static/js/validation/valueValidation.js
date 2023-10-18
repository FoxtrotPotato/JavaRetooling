let submitElement = document.getElementById("submitBtn");

document.getElementById("inputForm").addEventListener("input", function () {
    let valueElement = document.getElementById("pValue").value;

    if (valueElement === "" || valueElement <= 0) {
        submitElement.setAttribute("disabled", "disabled");
    } else {
        submitElement.removeAttribute("disabled");
    }
});