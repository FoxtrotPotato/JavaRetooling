let typeObject = document.getElementById("type");
let productObject = document.getElementById("product");
let quantityObject = document.getElementById("quantity");
let priceObject = document.getElementById("price");
let subtotalObject = document.getElementById("subtotal");
let observationsObject = document.getElementById("observations");
let totalObject = document.getElementById("total");

typeObject.addEventListener("change", calculateSubtotal);
quantityObject.addEventListener("change", calculateSubtotal);
priceObject.addEventListener("change", calculateSubtotal);
document.getElementById("save").addEventListener("click", save);

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
    let subtotal = parseFloat(subtotalObject.value);
    let observations = observationsObject.value;
    let total = parseInt(totalObject.value);

    if (type > 0 && product > 0 && quantity > 0 && price > 0) {
        let data = {
            type: type,
            product: product,
            quantity: quantity,
            price: price,
            subtotal: subtotal,
            observations: observations,
            total: total
        };

        let jsonData = JSON.stringify(data);

        console.log(jsonData);

        $.ajax({
            type: 'POST',
            url: '/transactions/save',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log(response);
            },
        });
    } else {
         alert ("Error. Missing Data. \n " +
            "Type, Product and Quantity must be filled.");
    }

}





