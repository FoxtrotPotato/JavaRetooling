let typeObject = document.getElementById("type");
let productObject = document.getElementById("product");
let quantityObject = document.getElementById("quantity");
let priceObject = document.getElementById("price");
let subtotalObject = document.getElementById("subtotal");
let observationsObject = document.getElementById("observations");
let totalObject = document.getElementById("total");
let csrfToken;
let csrfHeader;

typeObject.addEventListener("change", calculateSubtotal);
quantityObject.addEventListener("change", calculateSubtotal);
priceObject.addEventListener("change", calculateSubtotal);
document.getElementById("save").addEventListener("click", save);


function configureCsrfToken() {
//    let header = $("meta[name='_csrf_header']").attr("content");
//    csrfToken = $("meta[name='_csrf']").attr("content");

     csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
     csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");


    console.log(csrfToken + ", " + csrfHeader);

    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });
}

configureCsrfToken();

function calculateSubtotal() {
    let tempQuantity = parseFloat(quantityObject.value);
    let tempPrice = parseFloat(priceObject.value);
    let tempSubtotal = tempQuantity * tempPrice;
    if (tempQuantity > 0 && tempPrice > 0) {
        subtotalObject.innerHTML = tempSubtotal.toFixed(2);
        calculateTotal();
    }
}

function calculateTotal() {
    let tempType = parseInt(typeObject.value);
    let tempTotal = parseFloat(subtotalObject.textContent);
    if (tempType === 2) {
        tempTotal *= -1;
    }
    totalObject.innerHTML = tempTotal.toFixed(2);
}

function save() {
    let type = parseInt(typeObject.value);
    let product = parseInt(productObject.value);
    let quantity = parseFloat(quantityObject.value);
    let price = parseFloat(priceObject.value);
    let observations = observationsObject.value;
    let subTotal = parseFloat(subtotalObject.textContent);
    let total = parseFloat(totalObject.textContent);

    if (type > 0 && product > 0 && quantity > 0 && price > 0) {
        let data = {
            type: type,
            product: product,
            quantity: quantity,
            price: price,
            subtotal: subTotal,
            total: total,
            observations: observations
        };

        let jsonData = JSON.stringify(data);

        console.log(jsonData);

        $.ajax({
            type: 'POST',
            url: '/api/transactions/save',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: {'X-CSRF-TOKEN': csrfToken},
            success: function (response) {
                window.location.href = '/transactions/list';
                console.log("I SEE THIS AS AN ABSOLUTE WIN");

            },
            error: function (xhr, status, error) {
                console.log("Error en la solicitud AJAX:", error);
            }
        });
    } else {
        alert("Error. Missing Data. \n " +
            "Type, Product and Quantity must be filled.");
    }

}





