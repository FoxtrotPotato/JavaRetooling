package com.foxtrotpotato.chickentest.rest;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    private final UserService userService;

    @Autowired
    public TransactionRestController(TransactionService transactionService,
                                     TransactionDetailService transactionDetailService,
                                     ProductService productService,
                                     BalanceService balanceService,
                                     EggService eggService,
                                     ChickenService chickenService,
                                     UserService userService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody Map<String, Object> json) {
        System.out.println("begin");
        try {
            System.out.println(json);

            // get user's farm
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
            Farm theFarm = null;

            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                System.out.println(username);

                User theUser = userService.findByUserName(username);
                System.out.println(theUser);

                theFarm = theUser.getFarm();
                System.out.println(theFarm);
            }else {
                System.out.println("No authenticated user.");
            }

            // extract json data
            int type = (int) json.get("type");
            int productId = (int) json.get("product");
            int quantity = (int) json.get("quantity");
            float price = Float.parseFloat(String.valueOf(json.get("price")));
            String observations = String.valueOf(json.get("observations"));
            float subtotal = Float.parseFloat(String.valueOf(json.get("subtotal")));
            float total = Float.parseFloat(String.valueOf(json.get("total")));
            LocalDateTime date = LocalDateTime.now();

            System.out.println("{ type " + type + "\n" +
                    "product " + productId + "\n" +
                    "quantity " + quantity + "\n" +
                    "price " + price + "\n" +
                    "observations " + observations + "\n" +
                    "subtotal " + subtotal + "\n" +
                    "total " + total + "\n" +
                    "date " + date + "};");

            // Prepare Product
            Product theProduct = productService.findById(productId);
            System.out.println("control");
            int tempStock = (int) theProduct.getProductStock();
            System.out.println(tempStock);

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
            theTransaction.setFarm(theFarm);

            // Prepare Transaction Details
            TransactionDetail theTransactionDetail = new TransactionDetail();
            theTransactionDetail.setQuantity(quantity);
            theTransactionDetail.setPrice(price);
            theTransactionDetail.setSubtotal(subtotal);
            theTransactionDetail.setProduct(theProduct);
            theTransactionDetail.setTransaction(theTransaction);

            // Prepare Balance
            Float lastBalanceTotal;
            List<Balance> balanceList = balanceService.findAll();
            if (!balanceList.isEmpty()) {
                Balance lastBalance = balanceList.get(0);
                lastBalanceTotal = lastBalance.getBalanceTotal();
            } else {
                lastBalanceTotal = 0f;
            }

            String tempType;
            if (type == 1) {
                tempType = "IN";
            } else {
                tempType = "OUT";
            }

            Balance theBalance = new Balance();
            theBalance.setBalanceType(tempType);
            theBalance.setBalanceTotal(lastBalanceTotal + total);
            theBalance.setTransaction(theTransaction);
            theBalance.setFarm(theFarm);

            // Preview
            System.out.println(theTransaction);
            System.out.println(theTransactionDetail);
            System.out.println(theBalance);
            System.out.println(theProduct);

            // Save the rest
            productService.save(theProduct);
            System.out.println("product OK");
            transactionService.save(theTransaction);
            System.out.println("transaction OK");
            transactionDetailService.save(theTransactionDetail);
            System.out.println("transaction detail OK");
            balanceService.save(theBalance);
            System.out.println("balance OK");

            // delete/add eggs/chicken
            if (type == 1) {
                if (productId == 1) {
                    for (int i = 0; i < quantity; i++) {
                        List<Egg> eggsList = eggService.findAll();
                        Egg oldestEgg = eggsList.get(0);
                        int oldestEggId = oldestEgg.getEggId();
                        eggService.deleteById(oldestEggId);
                        System.out.println("deleted egg: " + oldestEggId);
                    }
                } else if (productId == 2) {
                    for (int i = 0; i < quantity; i++) {
                        List<Chicken> chickensList = chickenService.findAll();
                        Chicken oldestChicken = chickensList.get(0);
                        int oldestChickenId = oldestChicken.getChickenId();
                        chickenService.deleteById(oldestChickenId);
                        System.out.println("deleted chicken: " + oldestChickenId);
                    }
                }
            } else {
                System.out.println("add logic to create egg/chicken");
                if (productId == 1) {
                    for (int i = 0; i < quantity; i++) {
                        Egg theEgg = new Egg();
                        theEgg.setEggBirthDay(LocalDate.now());
                        theEgg.setProduct(theProduct);
                        theEgg.setFarm(theFarm);
                        eggService.save(theEgg);
                    }
                } else if (productId == 2) {
                    for (int i = 0; i < quantity; i++) {
                        Chicken theChicken = new Chicken();
                        theChicken.setChickenBirthDay(LocalDate.now());
                        theChicken.setProduct(theProduct);
                        theChicken.setFarm(theFarm);
                        chickenService.save(theChicken);
                    }
                }
            }


            return ResponseEntity.ok("Los datos se han enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error al guardar los datos.");
        }
    }

}
