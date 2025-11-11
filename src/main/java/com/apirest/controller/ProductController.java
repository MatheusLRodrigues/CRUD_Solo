package com.apirest.controller;


import com.apirest.model.Product;
import com.apirest.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> productsList = productServices.findAll();
        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable String id){
        return productServices.findById(id);
    }


    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productServices.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product){
        return productServices.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){
        productServices.deleteProduct(id);
    }
}
