package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.ProductRepository;
import com.foxtrotpotato.chickentest.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository theProductRepository) {
        productRepository = theProductRepository;
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
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteById(int theId) {
        productRepository.deleteById(theId);
    }

    @Override
    public ResponseEntity updateStock(String balanceType, int productId, int quantity, int maxCapacity) {
        Product theProduct = findById(productId);
        int excess;
        int tempStock = (int) theProduct.getProductStock();

        System.out.println("tempstock: " + tempStock);

        if (balanceType.equals("SALE")) {
            if (tempStock >= quantity) {
                tempStock = tempStock - quantity;
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough "+theProduct.getProductName()+"s stock available for the transaction.");
            }
        } else {
            tempStock = tempStock + quantity;

            if (tempStock > maxCapacity) {
                excess = tempStock - maxCapacity;
                tempStock = tempStock - excess;
                theProduct.setProductStock(tempStock);
                save(theProduct);
                return ResponseEntity.ok("Product updated successfully. \n" +
                         theProduct.getProductName() + "/s surplus sent to the nearest charity centre: " + excess);
            }
        }

        theProduct.setProductStock(tempStock);
        save(theProduct);
        System.out.println("product OK");
        return ResponseEntity.ok("Product updated successfully.");
    }

    @Override
    public float getProductPrice(int productId) {
        Optional<Product> theProduct = productRepository.findById(productId);
        float price = 0f;
        if (theProduct.isPresent()) {
            price = theProduct.get().getProductValue();
        }
        return price;
    }



}
