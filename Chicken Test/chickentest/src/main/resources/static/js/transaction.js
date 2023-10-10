let typeElement = document.getElementById("type");
let productElement = document.getElementById("product");
let quantityElement = document.getElementById("quantity");
let priceElement = document.getElementById("price");
let subtotalElement = document.getElementById("subtotal");
let observationsElement = document.getElementById("observations");
let totalElement = document.getElementById("total");
let messageElement = document.getElementById("message");
let csrfToken;
let csrfHeader;

productElement.addEventListener("change", getPrice);
typeElement.addEventListener("change", calculateSubtotal);
quantityElement.addEventListener("change", calculateSubtotal);
priceElement.addEventListener("change", calculateSubtotal);
document.getElementById("save").addEventListener("click", save);


function configureCsrfToken() {
    csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    console.log(csrfToken + ", " + csrfHeader);

    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });
}

configureCsrfToken();

function getPrice() {
    const theProductId = parseInt(productElement.value);
    console.log(theProductId);

    fetch(`/api/transactions/getPrice?productId=${theProductId}`)
        .then(function (response) {
            if (!response.ok) {
                throw new Error("Network Error");
            }
            return response.json();
        })
        .then(function (data) {
                const price = data;
                console.log(price);
                priceElement.value = parseFloat(price).toFixed(2);
            }
        ).catch(function (error) {
        console.error(error)
    });
}

function calculateSubtotal() {
    let tempQuantity = parseFloat(quantityElement.value);
    let tempPrice = parseFloat(priceElement.value);
    let tempSubtotal = tempQuantity * tempPrice;
    if (tempQuantity > 0 && tempPrice > 0) {
        subtotalElement.innerHTML = tempSubtotal.toFixed(2);
        calculateTotal();
    }
}

function calculateTotal() {
    let tempType = typeElement.options[typeElement.selectedIndex].text;
    let tempTotal = parseFloat(subtotalElement.textContent);
    if (tempType === "PURCHASE") {
        tempTotal *= -1;
    }
    totalElement.innerHTML = tempTotal.toFixed(2);
}

function save() {
    let type = typeElement.options[typeElement.selectedIndex].text;
    let product = parseInt(productElement.value);
    let quantity = parseFloat(quantityElement.value);
    let price = parseFloat(priceElement.value);
    let observations = observationsElement.value;
    let subTotal = parseFloat(subtotalElement.textContent);
    let total = parseFloat(totalElement.textContent);

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

    if (product !== 1 && product !== 2) {
        messageElement.innerHTML = "ERROR: Must select a product!";
        messageElement.style.display = "block";
    } else if (!(quantity > 0)) {
        messageElement.innerHTML = "ERROR: Must set a quantity!";
        messageElement.style.display = "block";
    } else if (!(price > 0)) {
        messageElement.innerHTML = "ERROR: Must set a price!";
        messageElement.style.display = "block";
    } else {
        $.ajax({
            type: 'POST',
            url: '/api/transactions/save',
            contentType: 'application/json',
            data: JSON.stringify(data),
            headers: {'X-CSRF-TOKEN': csrfToken},
            success: function () {
                window.location.href = '/transactions/list';
                console.log("I SEE THIS AS AN ABSOLUTE WIN");
            },
            error: function (xhr, status, error) {
                console.log("Error en la solicitud AJAX:", error);
                console.log(error);
                console.log(status);
                console.log(xhr);
                $('#message').html("ERROR: " + xhr.responseText).show();
            }
        });
    }

}





