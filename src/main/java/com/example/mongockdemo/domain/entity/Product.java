package com.example.mongockdemo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
  public Product(Product product) {
    this.id = product.id;
    this.price = product.price;
    this.name = product.name;
    this.description = product.description;
  }

  @Id
  private String id;
  private Double price;
  private String name;
  private String description;
}
