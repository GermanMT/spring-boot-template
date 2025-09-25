package es.nextdigital.demo.atm.repository;

import es.nextdigital.demo.atm.model.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {}
