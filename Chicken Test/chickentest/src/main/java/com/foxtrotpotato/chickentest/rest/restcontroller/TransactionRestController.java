package com.foxtrotpotato.chickentest.rest.restcontroller;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private final ParameterService parameterService;
    private final FarmService farmService;

    @Autowired
    public TransactionRestController(TransactionService transactionService,
                                     TransactionDetailService transactionDetailService,
                                     ProductService productService,
                                     BalanceService balanceService,
                                     EggService eggService,
                                     ChickenService chickenService,
                                     ParameterService parameterService,
                                     FarmService farmService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.parameterService = parameterService;
        this.farmService = farmService;
    }

    @GetMapping("/getPrice")
    public float getProductPrice(@RequestParam int productId) {
        return productService.getProductPrice(productId);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody Map<String, Object> json) {
        System.out.println("begin");

        try {
            System.out.println(json);

            // extract json data
            String balanceType = String.valueOf(json.get("type"));
            int productId = (int) json.get("product");
            int quantity = (int) json.get("quantity");
            float price = Float.parseFloat(String.valueOf(json.get("price")));
            String observations = String.valueOf(json.get("observations"));
            float subtotal = Float.parseFloat(String.valueOf(json.get("subtotal")));
            float total = Float.parseFloat(String.valueOf(json.get("total")));
            LocalDateTime date = LocalDateTime.now();

            Farm theFarm = farmService.getFarmByLoggedUser();

            // get farm parameters
            int maxCapacity = parameterService.findById(productId).getParameterValue();

            // Update Product
            productService.updateStock(balanceType, productId, quantity, maxCapacity);
            Product theProduct = productService.findById(productId);

            // Transaction
            Transaction theTransaction = new Transaction(date, total, observations, theFarm);

            // Transaction Details
            TransactionDetail theTransactionDetail = new TransactionDetail(theProduct, quantity, price, subtotal, theTransaction);

            // Prepare Balance
            Float lastBalance = balanceService.getLastBalance();
            Balance theBalance = new Balance(balanceType, total + lastBalance, theTransaction, theFarm);

            // Preview
            System.out.println(theTransaction + "\n" + theTransactionDetail + "\n" + theBalance + "\n" + theProduct);

            transactionService.save(theTransaction);
            System.out.println("transaction OK");
            transactionDetailService.save(theTransactionDetail);
            System.out.println("transaction detail OK");
            balanceService.save(theBalance);
            System.out.println("balance OK");

            // delete/add eggs/chicken
            if (productId == 1) {
                eggService.createDeleteEggs(balanceType, quantity, theProduct, theFarm);
            } else if (productId == 2) {
                chickenService.createDeleteChickens(balanceType, quantity, theProduct, theFarm);
            }

            return ResponseEntity.ok("Los datos se han enviado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error al guardar los datos.");
        }

    }


}


