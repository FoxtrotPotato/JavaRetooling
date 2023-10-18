let submitElement = document.getElementById("submitBtn");
let birthdayElement = document.getElementById("birthDay");

birthdayElement.addEventListener('input', function (event) {
    const inputDate = event.target.value;
    const reg = /(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d/;
    const invalidDates = ["30-02", "31-02", "31-04", "31-06", "31-09", "31-11"];
    let isInvalid;

    for (const invalidDate of invalidDates) {
        if (inputDate.includes(invalidDate)) {
            isInvalid = true;
            break;
        } else {
            isInvalid = false;
        }
    }

    if (inputDate.match(reg) && isInvalid === false) {
        submitElement.removeAttribute("disabled");
    } else if (inputDate.length === 8 && isInvalid === false && !inputDate.includes("-")) {
        event.target.value = inputDate.slice(0, 2) + '-' + inputDate.slice(2, 4) + '-' + inputDate.slice(4);
        submitElement.removeAttribute("disabled");
    } else {
        submitElement.setAttribute("disabled", "disabled");
    }

});