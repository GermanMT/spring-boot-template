package es.nextdigital.demo.client.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import es.nextdigital.demo.client.service.ClientService;
import es.nextdigital.demo.movement.dto.MovementDTO;
import es.nextdigital.demo.movement.model.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientController.class)
@DisplayName("ClientController Unit Tests")
class ClientControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ClientService clientService;

  private MovementDTO movementDTO1;
  private MovementDTO movementDTO2;

  @BeforeEach
  void setUp() {
    movementDTO1 = new MovementDTO();
    movementDTO1.setMovementAmount(new BigDecimal("100.00"));
    movementDTO1.setType(Type.CASH);

    movementDTO2 = new MovementDTO();
    movementDTO2.setMovementAmount(new BigDecimal("50.00"));
    movementDTO2.setType(Type.TRANSFER);
  }

  @Test
  @DisplayName("Should return movements when account exists and has movements")
  void getMovementsByAccount_ShouldReturnMovements_WhenAccountExists() throws Exception {
    Long accountId = 1L;
    List<MovementDTO> movements = Arrays.asList(movementDTO1, movementDTO2);

    when(clientService.getMovementsByAccountId(accountId)).thenReturn(movements);

    mockMvc
        .perform(
            get("/clients/{accountId}/movements", accountId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].movementAmount").value(100.00))
        .andExpect(jsonPath("$[0].type").value("CASH"))
        .andExpect(jsonPath("$[1].movementAmount").value(50.00))
        .andExpect(jsonPath("$[1].type").value("TRANSFER"));

    verify(clientService, times(1)).getMovementsByAccountId(accountId);
  }

  @Test
  @DisplayName("Should return 404 when account has no movements")
  void getMovementsByAccount_ShouldReturn404_WhenAccountHasNoMovements() throws Exception {
    Long accountId = 1L;

    when(clientService.getMovementsByAccountId(accountId))
        .thenThrow(new es.nextdigital.demo.client.exception.MovementsNotFoundException(accountId));

    mockMvc
        .perform(
            get("/clients/{accountId}/movements", accountId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(clientService, times(1)).getMovementsByAccountId(accountId);
  }

  @Test
  @DisplayName("Should handle large account ID")
  void getMovementsByAccount_ShouldHandleLargeAccountId() throws Exception {
    Long largeAccountId = 999999999L;
    List<MovementDTO> movements = Arrays.asList(movementDTO1);

    when(clientService.getMovementsByAccountId(largeAccountId)).thenReturn(movements);

    mockMvc
        .perform(
            get("/clients/{accountId}/movements", largeAccountId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));

    verify(clientService, times(1)).getMovementsByAccountId(largeAccountId);
  }

  @Test
  @DisplayName("Should return 400 when account ID is not a valid number")
  void getMovementsByAccount_ShouldReturn400_WhenAccountIdIsInvalid() throws Exception {
    mockMvc
        .perform(
            get("/clients/{accountId}/movements", "invalid-id")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verify(clientService, never()).getMovementsByAccountId(anyLong());
  }

  @Test
  @DisplayName("Should call service with correct account ID")
  void getMovementsByAccount_ShouldCallServiceWithCorrectId() throws Exception {
    Long accountId = 123L;
    List<MovementDTO> movements = Collections.emptyList();

    when(clientService.getMovementsByAccountId(accountId)).thenReturn(movements);

    mockMvc
        .perform(
            get("/clients/{accountId}/movements", accountId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(clientService, times(1)).getMovementsByAccountId(eq(accountId));
  }
}
