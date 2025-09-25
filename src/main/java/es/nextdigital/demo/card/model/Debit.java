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
}
