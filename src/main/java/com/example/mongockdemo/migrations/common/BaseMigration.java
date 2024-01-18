package com.example.mongockdemo.migrations.common;

import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseMigration {

  @RollbackExecution
  public void rollback() {
    log.error("Migration failed");
  }

  @RollbackBeforeExecution
  public void rollbackBefore() {
    log.error("Preparation to migrate failed");
  }

}