package com.example.mongockdemo.migrations._2_update_products;

import com.example.mongockdemo.domain.entity.Product;
import com.example.mongockdemo.domain.repository.ProductRepository;
import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ChangeUnit(id="updateUsingRepo", order = "6", author = "Dmytro Khonko")
@RequiredArgsConstructor
public class V006UpdateUsingRepo extends BaseMigration {
  private final ProductRepository productRepository;
  private List<Product> rollbackList = new ArrayList<>();

  @Execution
  public void changeSet() {
    var products = productRepository.findAllByName("apple");

    rollbackList = products.stream().map(Product::new).toList();

    for (Product product : products) {
      product.setName("new apple");
    }
    productRepository.saveAll(products);
  }

  @RollbackExecution
  public void rollback() {
    productRepository.saveAll(rollbackList);
  }
}
