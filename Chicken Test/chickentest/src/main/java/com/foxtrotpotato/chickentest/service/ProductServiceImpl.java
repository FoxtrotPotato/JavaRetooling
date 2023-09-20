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
        }
        else {
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
    public ResponseEntity updateStock(String balanceType, int productId, int quantity, int maxCapacity){
        Product theProduct = findById(productId);
        int excess;
        ResponseEntity response;
        int tempStock = (int) theProduct.getProductStock();

        System.out.println("tempstock: " + tempStock);

        if (balanceType == "SALE") {
            if (tempStock >= quantity) {
                tempStock = tempStock - quantity;
            } else {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay suficiente stock disponible para esta transacción.");
            }
        } else {
            tempStock = tempStock + quantity;

            if (tempStock > maxCapacity) {
                excess = tempStock - maxCapacity;
                tempStock = tempStock - excess;
                // manage excess (to be created)
            }
        }

        theProduct.setProductStock(tempStock);
        save(theProduct);
        System.out.println("product OK");
        response = ResponseEntity.ok("Producto actualizado correctamente.");
        return response;
    }

}
