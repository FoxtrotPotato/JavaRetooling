package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.ProductRepository;
import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.service.ProductService;
import com.foxtrotpotato.chickentest.util.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final GlobalData globalData;

    @Autowired
    public ProductServiceImpl(ProductRepository theProductRepository, GlobalData globalData) {
        productRepository = theProductRepository;
        this.globalData = globalData;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllByOrderByProductIdDesc();
    }

    @Override
    public Product findById(int theId) {
        Optional<Product> result = productRepository.findById(theId);
        Product theProduct;

        if (result.isPresent()) {
            theProduct = result.get();
        } else {
            throw new RuntimeException("Did not find Product id - " + theId);
        }

        return theProduct;
    }

    @Override
    @Transactional
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        productRepository.deleteById(theId);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateStock(String balanceType, int productId, int quantity, int maxCapacity) {
        Product theProduct = findById(productId);
        int excess;
        int tempStock = theProduct.getProductStock();
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");

        System.out.println("tempstock: " + tempStock);

        if (balanceType.equals("SALE") || balanceType.equals("HATCH") || balanceType.equals("DEATH")) {
            if (tempStock >= quantity) {
                tempStock = tempStock - quantity;

            } else {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough " + theProduct.getProductName() + "s stock available for the transaction.");
            }
        } else {
            tempStock = tempStock + quantity;

            if (tempStock > maxCapacity) {
                excess = tempStock - maxCapacity;
                tempStock = tempStock - excess;

                System.out.println(productId);

                switch (productId) {
                    case 1 -> {
                        globalData.setDiscardedEggs(globalData.getDiscardedEggs() + excess);
                        System.out.println(globalData.getDiscardedEggs());
                    }
                    case 2 -> {
                        globalData.setDiscardedChicken(globalData.getDiscardedChicken() + excess);
                        System.out.println(globalData.getDiscardedChicken());
                    }
                }

                response = ResponseEntity.ok("Product updated successfully. \n" +
                        theProduct.getProductName() + "/s surplus sent to the nearest charity centre: " + excess);
            }
        }

        theProduct.setProductStock(tempStock);
        save(theProduct);

        System.out.println("product OK");

        return response;
    }

    @Override
    public Double getProductPrice(int productId) {
        Optional<Product> theProduct = productRepository.findById(productId);
        Double price = 0d;
        if (theProduct.isPresent()) {
            price = theProduct.get().getProductValue();
        }
        return price;
    }


}
