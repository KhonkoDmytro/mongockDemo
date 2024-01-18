package com.example.mongockdemo.domain.repository;

import com.example.mongockdemo.domain.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
  List<Product> findAllByName(String apple);
}
