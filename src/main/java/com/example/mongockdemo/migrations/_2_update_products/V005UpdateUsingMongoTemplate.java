package com.example.mongockdemo.migrations._2_update_products;

import com.example.mongockdemo.domain.entity.Product;
import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@ChangeUnit(id="updateUsingMongoTemplate", order = "5", author = "Dmytro Khonko")
@RequiredArgsConstructor
public class V005UpdateUsingMongoTemplate extends BaseMigration {

  private final MongoTemplate mongoTemplate;
  private List<Product> rollbackList = new ArrayList<>();

  @Execution
  public void changeSet() {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is("apple"));
    List<Product> products  = mongoTemplate.find(query, Product.class);

    rollbackList = products.stream().map(Product::new).toList();

    for (Product product : products) {
      product.setDescription("new apple description");
      mongoTemplate.save(product);
//      throw new RuntimeException("ups, migration failed");
    }
  }

  @RollbackExecution
  public void rollback() {
    for (Product product : rollbackList) {
      mongoTemplate.save(product);
    }
  }
}
