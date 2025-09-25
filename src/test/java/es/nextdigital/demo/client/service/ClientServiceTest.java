package es.nextdigital.demo.client.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import es.nextdigital.demo.account.model.Account;
import es.nextdigital.demo.client.repository.ClientRepository;
import es.nextdigital.demo.movement.dto.MovementDTO;
import es.nextdigital.demo.movement.mapper.MovementMapper;
import es.nextdigital.demo.movement.model.Movement;
import es.nextdigital.demo.movement.model.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService Unit Tests")
class ClientServiceTest {

  @Mock private ClientRepository clientRepository;

  @Mock private MovementMapper movementMapper;

  @InjectMocks private ClientService clientService;

  private Account testAccount;
  private Movement testMovement1;
  private Movement testMovement2;
  private MovementDTO testMovementDTO1;
  private MovementDTO testMovementDTO2;

  @BeforeEach
  void setUp() {
    testAccount = new Account();
    testAccount.setId(1L);
    testAccount.setAccountAmount(new BigDecimal("1000.00"));

    testMovement1 = new Movement();
    testMovement1.setId(1L);
    testMovement1.setMovementAmount(new BigDecimal("100.00"));
    testMovement1.setType(Type.CASH);
    testMovement1.setBankAccount(testAccount);

    testMovement2 = new Movement();
    testMovement2.setId(2L);
    testMovement2.setMovementAmount(new BigDecimal("50.00"));
    testMovement2.setType(Type.TRANSFER);
    testMovement2.setBankAccount(testAccount);

    testMovementDTO1 = new MovementDTO();
    testMovementDTO1.setMovementAmount(new BigDecimal("100.00"));
    testMovementDTO1.setType(Type.CASH);

    testMovementDTO2 = new MovementDTO();
    testMovementDTO2.setMovementAmount(new BigDecimal("50.00"));
    testMovementDTO2.setType(Type.TRANSFER);
  }

  @Test
  @DisplayName("Should return movements when bank account has movements")
  void getMovementsByAccountId_ShouldReturnMovements_WhenAccountHasMovements() {
    Long bankAccountId = 1L;
    List<Movement> movements = Arrays.asList(testMovement1, testMovement2);
    List<MovementDTO> expectedMovementDTOs = Arrays.asList(testMovementDTO1, testMovementDTO2);

    when(clientRepository.findMovementsByBankAccountId(bankAccountId)).thenReturn(movements);
    when(movementMapper.toDtoList(movements)).thenReturn(expectedMovementDTOs);

    List<MovementDTO> result = clientService.getMovementsByAccountId(bankAccountId);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedMovementDTOs, result);

    verify(clientRepository, times(1)).findMovementsByBankAccountId(bankAccountId);
    verify(movementMapper, times(1)).toDtoList(movements);
  }

  @Test
  @DisplayName("Should throw exception when bank account has no movements")
  void getMovementsByAccountId_ShouldThrowException_WhenAccountHasNoMovements() {
    Long bankAccountId = 1L;
    List<Movement> emptyMovements = Collections.emptyList();

    when(clientRepository.findMovementsByBankAccountId(bankAccountId)).thenReturn(emptyMovements);

    assertThrows(
        es.nextdigital.demo.client.exception.MovementsNotFoundException.class,
        () -> clientService.getMovementsByAccountId(bankAccountId));

    verify(clientRepository, times(1)).findMovementsByBankAccountId(bankAccountId);
  }

  @Test
  @DisplayName("Should call repository with correct bank account ID and throw exception when empty")
  void getMovementsByAccountId_ShouldCallRepositoryWithCorrectId() {

    Long bankAccountId = 999L;
    List<Movement> movements = Collections.emptyList();

    when(clientRepository.findMovementsByBankAccountId(bankAccountId)).thenReturn(movements);

    assertThrows(
        es.nextdigital.demo.client.exception.MovementsNotFoundException.class,
        () -> clientService.getMovementsByAccountId(bankAccountId));

    verify(clientRepository, times(1)).findMovementsByBankAccountId(eq(bankAccountId));
  }

  @Test
  @DisplayName("Should handle null bank account ID and throw exception")
  void getMovementsByAccountId_ShouldHandleNullId() {
    Long bankAccountId = null;
    List<Movement> movements = Collections.emptyList();

    when(clientRepository.findMovementsByBankAccountId(bankAccountId)).thenReturn(movements);

    assertThrows(
        es.nextdigital.demo.client.exception.MovementsNotFoundException.class,
        () -> clientService.getMovementsByAccountId(bankAccountId));

    verify(clientRepository, times(1)).findMovementsByBankAccountId(null);
  }
}
