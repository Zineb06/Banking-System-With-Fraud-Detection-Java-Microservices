package com.banking.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.accountservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
