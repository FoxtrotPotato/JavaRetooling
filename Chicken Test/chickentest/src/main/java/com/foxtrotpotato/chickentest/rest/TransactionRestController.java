package com.foxtrotpotato.chickentest.rest;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/transactions")
public class TransactionRestController {
    private final TransactionService transactionService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final EggService eggService;
    private final ChickenService chickenService;
    private final TransactionDetailService transactionDetailService;

    @Autowired
    public TransactionRestController(TransactionService transactionService,
                                     TransactionDetailService transactionDetailService,
                                     ProductService productService,
                                     BalanceService balanceService,
                                     EggService eggService,
                                     ChickenService chickenService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody Map<String, Object> json) {
        try {
            // extract json data
            int type = (int) json.get("type");
            int productId = (int) json.get("product");
            int quantity = (int) json.get("quantity");
            float price = (float) json.get("price");
            String observations = String.valueOf(json.get("observations"));
            float subTotal = (float) json.get("subTotal");
            float total = (float) json.get("total");
            LocalDateTime date = LocalDateTime.now();

            // Prepare Product
            Product theProduct = productService.findById(productId);
            int tempStock = (int) theProduct.getProductStock();

            if (type == 1) {
                if (tempStock >= quantity) {
                    tempStock = tempStock - quantity;
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay suficiente stock disponible para esta transacción.");
                }
            } else {
                tempStock = tempStock + quantity;
            }

            theProduct.setProductStock(tempStock);

            // Prepare Transaction
            Transaction theTransaction = new Transaction();
            theTransaction.setTransactionDate(date);
            theTransaction.setTransactionTotal(total);
            theTransaction.setTransactionObservations(observations);

            // Prepare Transaction Details
            TransactionDetail theTransactionDetail = new TransactionDetail();
            theTransactionDetail.setQuantity(quantity);
            theTransactionDetail.setPrice(price);
            theTransactionDetail.setSubtotal(subTotal);
            theTransactionDetail.setProduct(theProduct);

            // Prepare Balance
            String tempType;
            if (type == 1) {
                tempType = "IN";
            } else {
                tempType = "OUT";
            }
            Balance theBalance = new Balance();
            theBalance.setBalanceType(tempType);
            theBalance.setBalanceTotal(total);

            // Save all
            productService.save(theProduct);
            transactionService.save(theTransaction);
            transactionDetailService.save(theTransactionDetail);
            balanceService.save(theBalance);

            // delete eggs/chicken
            if (productId == 1) {
                for (int i = 0; i < quantity; i++) {
                    List<Egg> eggsList = eggService.findAll();
                    Egg oldestEgg = eggsList.get(0);
                    int oldestEggId = oldestEgg.getEggId();
                    eggService.deleteById(oldestEggId);
                }
            } else {
                for (int i = 0; i < quantity; i++) {
                    List<Chicken> chickensList = chickenService.findAll();
                    Chicken oldestChicken = chickensList.get(0);
                    int oldestChickenId = oldestChicken.getChickenId();
                    chickenService.deleteById(oldestChickenId);
                }
            }

            return ResponseEntity.ok("Los datos se han enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error al guardar los datos.");
        }
    }

}
