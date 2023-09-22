package com.foxtrotpotato.chickentest.rest.restservice;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionRestServiceImpl implements TransactionRestService {

    private final TransactionService transactionService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final EggService eggService;
    private final ChickenService chickenService;
    private final TransactionDetailService transactionDetailService;
    private final UserService userService;
    private final ParameterService parameterService;
    private final FarmService farmService;

    @Autowired
    public TransactionRestServiceImpl(TransactionService transactionService,
                                      TransactionDetailService transactionDetailService,
                                      ProductService productService,
                                      BalanceService balanceService,
                                      EggService eggService,
                                      ChickenService chickenService,
                                      UserService userService,
                                      ParameterService parameterService,
                                      FarmService farmService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.userService = userService;
        this.parameterService = parameterService;
        this.farmService = farmService;
    }

    public ResponseEntity<String> saveTransaction(Map<String, Object> json) {
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

    @Override
    public int eggSalesCount() {
        int eggSalesCount = 0;
        List<TransactionDetail> transactionDetailList = transactionDetailService.findAll();
        if (!transactionDetailList.isEmpty()) {
            for (int i = 0; i < transactionDetailList.size(); i++) {
                int tempTransaction = transactionDetailList.get(i).getTransaction().getTransactionId();
                String balanceType = balanceService.findById(tempTransaction).getBalanceType();
                int productId = transactionDetailList.get(i).getProduct().getProductId();

                if (balanceType.equals("SALE") && productId == 1) {
                    eggSalesCount++;
                }
            }
        }
        return eggSalesCount;
    }

    @Override
    public int eggPurchasesCount() {
        int eggPurchasesCount = 0;
        List<TransactionDetail> transactionDetailList = transactionDetailService.findAll();
        if (!transactionDetailList.isEmpty()) {
            for (int i = 0; i < transactionDetailList.size(); i++) {
                int tempTransaction = transactionDetailList.get(i).getTransaction().getTransactionId();
                String balanceType = balanceService.findById(tempTransaction).getBalanceType();
                int productId = transactionDetailList.get(i).getProduct().getProductId();

                if (balanceType.equals("PURCHASE") && productId == 1) {
                    eggPurchasesCount++;
                }
            }
        }
        return eggPurchasesCount;
    }


    @Override
    public int chickenSalesCount() {
        int chickenSalesCount = 0;
        List<TransactionDetail> transactionDetailList = transactionDetailService.findAll();
        if (!transactionDetailList.isEmpty()) {
            for (int i = 0; i < transactionDetailList.size(); i++) {
                int tempTransaction = transactionDetailList.get(i).getTransaction().getTransactionId();
                String balanceType = balanceService.findById(tempTransaction).getBalanceType();
                int productId = transactionDetailList.get(i).getProduct().getProductId();

                if (balanceType.equals("SALE") && productId == 2) {
                    chickenSalesCount++;
                }
            }
        }
        return chickenSalesCount;
    }

    @Override
    public int chickenPurchasesCount() {
        int chickenPurchasesCount = 0;
        List<TransactionDetail> transactionDetailList = transactionDetailService.findAll();
        if (!transactionDetailList.isEmpty()) {
            for (int i = 0; i < transactionDetailList.size(); i++) {
                int tempTransaction = transactionDetailList.get(i).getTransaction().getTransactionId();
                String balanceType = balanceService.findById(tempTransaction).getBalanceType();
                int productId = transactionDetailList.get(i).getProduct().getProductId();

                if (balanceType.equals("PURCHASE") && productId == 2) {
                    chickenPurchasesCount++;
                }
            }
        }
        return chickenPurchasesCount;
    }

}
