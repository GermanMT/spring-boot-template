package es.nextdigital.demo.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.nextdigital.demo.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  
}
