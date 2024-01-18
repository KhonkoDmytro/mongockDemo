package com.example.mongockdemo.migrations._1_create_product;

import com.example.mongockdemo.domain.entity.Product;
import com.example.mongockdemo.domain.repository.ProductRepository;
import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import lombok.RequiredArgsConstructor;

@ChangeUnit(id="createProductUsingRepo", order = "2", author = "Dmytro Khonko")
@RequiredArgsConstructor
public class V002CreateUsingRepo extends BaseMigration {
  private final ProductRepository productRepository;

  @Execution
  public void changeSet() {
    var product = Product.builder()
            .price(35.0)
            .name("apple")
            .description("red and sweet")
            .build();
    productRepository.save(product);
  }
}
