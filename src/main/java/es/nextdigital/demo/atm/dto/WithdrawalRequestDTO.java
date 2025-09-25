package es.nextdigital.demo.atm.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalRequestDTO {
  @NotBlank
  private String cardNumber;

  @NotNull
  @Positive
  private BigDecimal amount;

  @NotNull
  private Long atmId;
}