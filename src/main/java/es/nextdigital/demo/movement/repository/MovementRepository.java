package es.nextdigital.demo.movement.repository;

import es.nextdigital.demo.movement.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {}
