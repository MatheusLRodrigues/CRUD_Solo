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
}
