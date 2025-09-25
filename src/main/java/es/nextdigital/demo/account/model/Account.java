package es.nextdigital.demo.account.model;

import java.math.BigDecimal;
import java.util.List;

import es.nextdigital.demo.client.model.Client;
import es.nextdigital.demo.movement.model.Movement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "accounts")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @PositiveOrZero
  private BigDecimal accountAmount;

  @OneToOne(mappedBy = "bankAccount")
  private Client client;

  @OneToMany(mappedBy = "bankAccount")
  private List<Movement> movements;
}
