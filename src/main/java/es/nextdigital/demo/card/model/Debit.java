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
public class Debit extends Card {
  @NotNull
  @PositiveOrZero
  private BigDecimal dailyLimit;

  public boolean hasEnoughBalance(BigDecimal amount) {
    return getBankAccount().getAccountAmount().compareTo(amount) >= 0;
  }

  public boolean isWithinDailyLimit(BigDecimal amount) {
    return dailyLimit.compareTo(amount) >= 0;
  }
}
