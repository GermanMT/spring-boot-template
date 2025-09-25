package es.nextdigital.demo.atm.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atms")
public class ATM {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String bankName;

  @NotBlank
  private String location;

  @NotNull
  @PositiveOrZero
  private BigDecimal externalBankFee;

  @NotNull
  @PositiveOrZero
  private BigDecimal availableCash;

  public BigDecimal calculateFee(String cardBankName) {
    return this.bankName.equalsIgnoreCase(cardBankName) ? BigDecimal.ZERO : externalBankFee;
  }
}