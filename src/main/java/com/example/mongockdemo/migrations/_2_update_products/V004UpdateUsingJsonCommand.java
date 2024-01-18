package com.example.mongockdemo.migrations._2_update_products;

import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id="updateUsingCommand", order = "4", author = "Dmytro Khonko")
@RequiredArgsConstructor
public class V004UpdateUsingJsonCommand extends BaseMigration {
  private final MongoTemplate mongoTemplate;

  @Execution
  public void changeSet() {
    mongoTemplate.executeCommand("""
            
            {
              update: "product",
              updates: [
                {
                  q: { name: "apple" },
                  u: {
                    $set: {
                      "price" :  25.5
                    }
                  }
                }
              ]
            }
            """);
  }
}
