package ru.iteco.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.account.model.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
