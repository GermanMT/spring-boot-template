package es.nextdigital.demo.movement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.nextdigital.demo.movement.model.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
  
}
