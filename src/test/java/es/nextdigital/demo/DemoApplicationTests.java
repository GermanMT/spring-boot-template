package es.nextdigital.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Demo Application Context Tests")
class DemoApplicationTests {

  @Test
  @DisplayName("Should load Spring Boot application context successfully")
  void contextLoads() {}
}
