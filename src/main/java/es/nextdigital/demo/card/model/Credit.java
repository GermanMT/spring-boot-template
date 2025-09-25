package es.nextdigital.demo.card.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
public class Credit extends Card {
  @NotNull
  @PositiveOrZero
  private BigDecimal creditLimit;

  @NotNull
  @PositiveOrZero
  private BigDecimal usedCredit = BigDecimal.ZERO;

  public BigDecimal getAvailableCredit() {
    return creditLimit.subtract(usedCredit);
  }

  public boolean hasAvailableCredit(BigDecimal amount) {
    return getAvailableCredit().compareTo(amount) >= 0;
  }
}
