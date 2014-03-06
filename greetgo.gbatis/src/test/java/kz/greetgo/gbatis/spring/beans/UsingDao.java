package kz.greetgo.gbatis.spring.beans;

import kz.greetgo.gbatis.spring.daos.ClientDao6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsingDao {
  
  @Autowired
  private ClientDao6 clientDao6;
  
  public void prepareDbData() {
    for (int age = 10; age <= 20; age++) {
      long id = 3000 + age;
      clientDao6.insClient(id);
      clientDao6.insClientSurname(id, "Sur" + id);
      clientDao6.insClientName(id, "Name" + age);
      
      clientDao6.insClientAge(id, age);
    }
  }
}
