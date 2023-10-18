package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService theProductService) {
        productService = theProductService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/list")
    public String listProduct(Model theModel) {
        List<Product> theProducts = productService.findAll();
        theModel.addAttribute("products", theProducts);

        return "products/list-products";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/showUpdateProductForm")
    public String showFormForUpdate(@RequestParam("productId") int theId, Model theModel) {

        Product theProduct = productService.findById(theId);
        theModel.addAttribute("product", theProduct);

        return "products/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product theProduct) {
        productService.save(theProduct);
        return "redirect:/products/list";
    }

}
