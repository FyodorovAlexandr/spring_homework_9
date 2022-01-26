package ru.iteco.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.account.model.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

}
