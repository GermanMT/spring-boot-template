package es.nextdigital.demo.movement.dto;

import java.math.BigDecimal;

import es.nextdigital.demo.movement.model.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
  
  @NotNull private BigDecimal movementAmount;

  private Type type;
  
}
