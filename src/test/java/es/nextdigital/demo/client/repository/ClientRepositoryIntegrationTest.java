package es.nextdigital.demo.client.repository;

import static org.junit.jupiter.api.Assertions.*;

import es.nextdigital.demo.account.model.Account;
import es.nextdigital.demo.client.model.Client;
import es.nextdigital.demo.movement.model.Movement;
import es.nextdigital.demo.movement.model.Type;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ClientRepository Integration Tests")
class ClientRepositoryIntegrationTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private ClientRepository clientRepository;

  private Client testClient;
  private Account testAccount;
  private Movement movement1;
  private Movement movement2;
  private Movement movement3;

  @BeforeEach
  void setUp() {
    // First persist the account
    testAccount = new Account();
    testAccount.setAccountAmount(new BigDecimal("1000.00"));
    entityManager.persistAndFlush(testAccount);

    // Then create client with the persisted account
    testClient = new Client();
    testClient.setName("John Doe");
    testClient.setEmail("john.doe@example.com");
    testClient.setBankAccount(testAccount);
    entityManager.persistAndFlush(testClient);

    // Create movements
    movement1 = new Movement();
    movement1.setMovementAmount(new BigDecimal("100.00"));
    movement1.setType(Type.CASH);
    movement1.setBankAccount(testAccount);

    movement2 = new Movement();
    movement2.setMovementAmount(new BigDecimal("50.00"));
    movement2.setType(Type.TRANSFER);
    movement2.setBankAccount(testAccount);

    movement3 = new Movement();
    movement3.setMovementAmount(new BigDecimal("25.00"));
    movement3.setType(Type.FEE);
    movement3.setBankAccount(testAccount);

    entityManager.persistAndFlush(movement1);
    entityManager.persistAndFlush(movement2);
    entityManager.persistAndFlush(movement3);
  }

  @Test
  @DisplayName("Should find all movements by bank account ID")
  void findMovementsByBankAccountId_ShouldReturnAllMovements_WhenAccountExists() {
    List<Movement> movements = clientRepository.findMovementsByBankAccountId(testAccount.getId());

    assertNotNull(movements);
    assertEquals(3, movements.size());

    movements.forEach(
        movement -> assertEquals(testAccount.getId(), movement.getBankAccount().getId()));

    assertTrue(
        movements.stream()
            .anyMatch(
                m ->
                    m.getMovementAmount().equals(new BigDecimal("100.00"))
                        && m.getType() == Type.CASH));
    assertTrue(
        movements.stream()
            .anyMatch(
                m ->
                    m.getMovementAmount().equals(new BigDecimal("50.00"))
                        && m.getType() == Type.TRANSFER));
    assertTrue(
        movements.stream()
            .anyMatch(
                m ->
                    m.getMovementAmount().equals(new BigDecimal("25.00"))
                        && m.getType() == Type.FEE));
  }

  @Test
  @DisplayName("Should return empty list when account has no movements")
  void findMovementsByBankAccountId_ShouldReturnEmptyList_WhenAccountHasNoMovements() {
    Account emptyAccount = new Account();
    emptyAccount.setAccountAmount(new BigDecimal("500.00"));
    entityManager.persistAndFlush(emptyAccount);

    List<Movement> movements = clientRepository.findMovementsByBankAccountId(emptyAccount.getId());

    assertNotNull(movements);
    assertTrue(movements.isEmpty());
  }

  @Test
  @DisplayName("Should return empty list when account does not exist")
  void findMovementsByBankAccountId_ShouldReturnEmptyList_WhenAccountDoesNotExist() {
    Long nonExistentAccountId = 999999L;

    List<Movement> movements = clientRepository.findMovementsByBankAccountId(nonExistentAccountId);

    assertNotNull(movements);
    assertTrue(movements.isEmpty());
  }

  @Test
  @DisplayName("Should not return movements from other accounts")
  void findMovementsByBankAccountId_ShouldNotReturnMovementsFromOtherAccounts() {
    Account otherAccount = new Account();
    otherAccount.setAccountAmount(new BigDecimal("2000.00"));
    entityManager.persistAndFlush(otherAccount);

    Movement otherMovement = new Movement();
    otherMovement.setMovementAmount(new BigDecimal("200.00"));
    otherMovement.setType(Type.CASH);
    otherMovement.setBankAccount(otherAccount);
    entityManager.persistAndFlush(otherMovement);

    List<Movement> movements = clientRepository.findMovementsByBankAccountId(testAccount.getId());

    assertNotNull(movements);
    assertEquals(3, movements.size());

    movements.forEach(
        movement -> assertNotEquals(otherAccount.getId(), movement.getBankAccount().getId()));
  }

  @Test
  @DisplayName("Should handle null bank account ID")
  void findMovementsByBankAccountId_ShouldHandleNullAccountId() {
    List<Movement> movements = clientRepository.findMovementsByBankAccountId(null);

    assertNotNull(movements);
    assertTrue(movements.isEmpty());
  }

  @Test
  @DisplayName("Should return movements with all required fields populated")
  void findMovementsByBankAccountId_ShouldReturnMovementsWithAllFields() {
    List<Movement> movements = clientRepository.findMovementsByBankAccountId(testAccount.getId());

    assertNotNull(movements);
    assertFalse(movements.isEmpty());

    movements.forEach(
        movement -> {
          assertNotNull(movement.getId());
          assertNotNull(movement.getMovementAmount());
          assertNotNull(movement.getType());
          assertNotNull(movement.getBankAccount());
          assertNotNull(movement.getBankAccount().getId());
        });
  }
}
