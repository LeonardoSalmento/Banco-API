package com.example.bankProject.repository;

import com.example.bankProject.model.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
    List<BankStatement> findByAccountId(Long accountId);
}
