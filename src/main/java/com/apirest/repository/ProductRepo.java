package com.apirest.repository;

import com.apirest.model.Product;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
}
