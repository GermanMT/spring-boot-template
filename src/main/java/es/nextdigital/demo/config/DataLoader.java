package es.nextdigital.demo.config;

import es.nextdigital.demo.account.model.Account;
import es.nextdigital.demo.account.repository.AccountRepository;
import es.nextdigital.demo.atm.model.ATM;
import es.nextdigital.demo.atm.repository.ATMRepository;
import es.nextdigital.demo.card.model.Credit;
import es.nextdigital.demo.card.model.Debit;
import es.nextdigital.demo.card.repository.CardRepository;
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
      MovementRepository movementRepository,
      CardRepository cardRepository,
      ATMRepository atmRepository) {
    return args -> {

      Account account1 = new Account();
      account1.setAccountAmount(BigDecimal.valueOf(1000));
      account1 = accountRepository.save(account1);

      Account account2 = new Account();
      account2.setAccountAmount(BigDecimal.valueOf(2000));
      account2 = accountRepository.save(account2);

      Client client1 = new Client();
      client1.setName("Juan Pérez");
      client1.setEmail("juan.perez@example.com");
      client1.setBankAccount(account1);
      clientRepository.save(client1);

      Client client2 = new Client();
      client2.setName("María García");
      client2.setEmail("maria.garcia@example.com");
      client2.setBankAccount(account2);
      clientRepository.save(client2);

      Debit debitCard = new Debit();
      debitCard.setCardNumber("1234567890123456");
      debitCard.setBankName("Banco Santander");
      debitCard.setBankAccount(account1);
      debitCard.setDailyLimit(BigDecimal.valueOf(500));
      cardRepository.save(debitCard);

      Credit creditCard = new Credit();
      creditCard.setCardNumber("9876543210987654");
      creditCard.setBankName("BBVA");
      creditCard.setBankAccount(account2);
      creditCard.setCreditLimit(BigDecimal.valueOf(1000));
      creditCard.setUsedCredit(BigDecimal.ZERO);
      cardRepository.save(creditCard);

      ATM atm1 = new ATM();
      atm1.setBankName("Banco Santander");
      atm1.setLocation("Plaza Mayor 1, Madrid");
      atm1.setExternalBankFee(BigDecimal.ZERO);
      atm1.setAvailableCash(BigDecimal.valueOf(10000));
      atmRepository.save(atm1);

      ATM atm2 = new ATM();
      atm2.setBankName("BBVA");
      atm2.setLocation("Gran Vía 25, Madrid");
      atm2.setExternalBankFee(BigDecimal.valueOf(2.50));
      atm2.setAvailableCash(BigDecimal.valueOf(5000));
      atmRepository.save(atm2);

      Movement movement1 = new Movement();
      movement1.setMovementAmount(BigDecimal.valueOf(200));
      movement1.setType(Type.CASH);
      movement1.setBankAccount(account1);

      Movement movement2 = new Movement();
      movement2.setMovementAmount(BigDecimal.valueOf(50));
      movement2.setType(Type.TRANSFER);
      movement2.setBankAccount(account1);

      movementRepository.saveAll(Arrays.asList(movement1, movement2));
    };
  }
}
