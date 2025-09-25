package es.nextdigital.demo.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.nextdigital.demo.movement.dto.MovementDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Client Integration Tests")
class ClientIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Should return movements when account exists and has movements (using preloaded data)")
  void getMovementsByAccount_ShouldReturnMovements_WhenAccountExists() throws Exception {
    String url = "http://localhost:" + port + "/clients/1";
    
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    
    List<MovementDTO> movements = objectMapper.readValue(
        response.getBody(), 
        new TypeReference<List<MovementDTO>>() {}
    );
    
    assertFalse(movements.isEmpty());
    assertTrue(movements.stream().allMatch(m -> m.getMovementAmount() != null));
    assertTrue(movements.stream().allMatch(m -> m.getType() != null));
  }

  @Test
  @DisplayName("Should return 404 when account has no movements")
  void getMovementsByAccount_ShouldReturn404_WhenAccountHasNoMovements() {
    String url = "http://localhost:" + port + "/clients/999";
    
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  @DisplayName("Should return 400 for invalid account ID format")
  void getMovementsByAccount_ShouldReturn400_WhenInvalidIdFormat() {
    String url = "http://localhost:" + port + "/clients/invalid-id";
    
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}