package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.ProductRepository;
import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.service.ProductService;
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
    public void deleteList(List<Product> products) {
        for (Product product : products) {
            productRepository.deleteById(product.getProductId());
        }
    }

    @Override
    public ResponseEntity<String> updateStock(String balanceType, int productId, int quantity, int maxCapacity) {
        Product theProduct = findById(productId);
        int excess;
        int tempStock = theProduct.getProductStock();
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");

        System.out.println("tempstock: " + tempStock);

        if (balanceType.equals("SALE") || balanceType.equals("HATCH") || balanceType.equals("DEATH")){
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
