package kz.greetgo.gbatis.util.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.sql.Connection;

import kz.greetgo.gbatis.util.test.TestBase;

import org.testng.annotations.Test;

public class AbstractUtilRegisterTest extends TestBase {
  
  @Test(dataProvider = "connectProvider")
  public void con(Connection con) throws Exception {
    
    assertThat(1);
  }
  
}
