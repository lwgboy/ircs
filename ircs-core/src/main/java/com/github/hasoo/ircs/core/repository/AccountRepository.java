package com.github.hasoo.ircs.core.repository;


import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import com.github.hasoo.ircs.core.entity.Account;

public interface AccountRepository extends Repository<Account, Long> {

  Collection<Account> findAll();

  Optional<Account> findByUsername(String username);

  Optional<Account> findById(Long id);

  Integer countByUsername(String username);

  Account save(Account account);

  void deleteAccountById(Long id);

  @Modifying
  @Query("update Account a set a.fee = a.fee + :fee where a.username = :username")
  int updateAccountSetFee(@Param("fee") double fee, @Param("username") String username);
}
