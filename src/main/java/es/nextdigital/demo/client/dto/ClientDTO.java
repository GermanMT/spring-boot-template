package es.nextdigital.demo.client.dto;

import java.util.List;

import es.nextdigital.demo.movement.dto.MovementDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String email;

  @NotNull
  private List<MovementDTO> movements;
}
