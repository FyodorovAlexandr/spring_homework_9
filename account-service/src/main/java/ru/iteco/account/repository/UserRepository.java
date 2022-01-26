package ru.iteco.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.account.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
