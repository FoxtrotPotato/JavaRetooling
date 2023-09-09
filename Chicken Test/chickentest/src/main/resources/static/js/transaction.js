let invoiceTable = document.getElementById("invoiceTable");
let totalElement = document.getElementById("total");
let rowCount = 1;  // generar IDs únicos para cada fila

function addRow() {
    let newRow = invoiceTable.insertRow();
    newRow.id = "row" + rowCount;

    let productCell = newRow.insertCell(0);
    let quantityCell = newRow.insertCell(1);
    let priceCell = newRow.insertCell(2);
    let subtotalCell = newRow.insertCell(3);
    let actionsCell = newRow.insertCell(4);

    productCell.innerHTML = '<input type="text" id="product' + rowCount + '">';
    quantityCell.innerHTML = '<input type="number" id="quantity' + rowCount + '" onchange="calculateSubtotal(' + rowCount + ')">';
    priceCell.innerHTML = '<input type="number" id="price' + rowCount + '" onchange="calculateSubtotal(' + rowCount + ')">';
    subtotalCell.innerHTML = '<span id="subtotal' + rowCount + '">0</span>';
    actionsCell.innerHTML = '<button onclick="deleteRow(' + rowCount + ')">Eliminar</button>';

    rowCount++;
}

function calculateSubtotal(row) {
    let quantity = parseFloat(document.getElementById("quantity" + row).value);
    let price = parseFloat(document.getElementById("price" + row).value);
    let subtotal = quantity * price;
    document.getElementById("subtotal" + row).textContent = subtotal.toFixed(2);
    calculateTotal();
}

function calculateTotal() {
    let subtotals = document.querySelectorAll("[id^='total']");
    let total = 0;
    subtotals.forEach(function(subtotal) {
        total += parseFloat(subtotal.textContent);
    });
    totalElement.textContent = total.toFixed(2);
}

function deleteRow(row) {
    document.getElementById("row" + row).remove();
    calculateTotal();
}
