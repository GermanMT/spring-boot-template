package es.nextdigital.demo.config;

import es.nextdigital.demo.account.model.Account;
import es.nextdigital.demo.account.repository.AccountRepository;
import es.nextdigital.demo.client.model.Client;
import es.nextdigital.demo.client.repository.ClientRepository;
import es.nextdigital.demo.movement.model.Movement;
import es.nextdigital.demo.movement.model.Type;
import es.nextdigital.demo.movement.repository.MovementRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

  @Bean
  CommandLineRunner initData(
      ClientRepository clientRepository,
      AccountRepository accountRepository,
      MovementRepository movementRepository) {
    return args -> {

      // Accounts
      Account account1 = new Account();
      account1.setAccountAmount(BigDecimal.valueOf(1000));

      // Clients
      Client client1 = new Client();
      client1.setName("Juan Pérez");
      client1.setEmail("juan.perez@example.com");
      client1.setBankAccount(account1);
      clientRepository.save(client1);

      // Movements
      Movement movement1 = new Movement();
      movement1.setMovementAmount(BigDecimal.valueOf(200));
      movement1.setType(Type.CASH);
      movement1.setBankAccount(account1);

      Movement movement2 = new Movement();
      movement2.setMovementAmount(BigDecimal.valueOf(50));
      movement2.setType(Type.TRANSFER);
      movement2.setBankAccount(account1);

      movementRepository.saveAll(Arrays.asList(movement1, movement2));
      ;
    };
  }
}
