package es.nextdigital.demo.client.repository;

import es.nextdigital.demo.client.model.Client;
import es.nextdigital.demo.movement.model.Movement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query("SELECT m FROM Movement m WHERE m.bankAccount.id = :bankAccountId")
  List<Movement> findMovementsByBankAccountId(@Param("bankAccountId") Long bankAccountId);
}
