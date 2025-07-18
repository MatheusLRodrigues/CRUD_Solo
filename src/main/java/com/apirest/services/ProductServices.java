package com.apirest.services;


import com.apirest.model.Product;
import com.apirest.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAll(){
        return productRepo.findAll();
    }

    public Product findById(String id){
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    public Product updateProduct(String id, Product updatedData){
        Product product = findById(id);
        product.setProductName(updatedData.getProductName());
        product.setProductDescription(updatedData.getProductDescription());

        return productRepo.save(product);
    }

    public void deleteProduct(String id){
        productRepo.deleteById(id);
    }

}
