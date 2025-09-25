package es.nextdigital.demo.card.repository;

import es.nextdigital.demo.card.model.Card;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
  Optional<Card> findByCardNumber(String cardNumber);
}