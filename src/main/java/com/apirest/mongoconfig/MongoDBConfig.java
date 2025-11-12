package com.apirest.mongoconfig;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {

    @Bean(name = "imagesMongoTemplate")
    public MongoTemplate imagesMongoTemplate(){
        MongoClient client = MongoClients.create("mongodb://localhost:27017/Images");
        return new MongoTemplate(client, "Images");
    }

    @Bean(name = "productMongoTemplate")
    public MongoTemplate productMongoTemplate(){
        MongoClient client = MongoClients.create("mongodb://localhost:27017/Products");
        return new MongoTemplate(client, "Products");
    }

}
