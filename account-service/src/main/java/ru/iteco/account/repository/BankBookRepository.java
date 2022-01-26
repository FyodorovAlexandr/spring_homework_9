package ru.iteco.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.iteco.account.model.entity.BankBookEntity;
import ru.iteco.account.model.entity.CurrencyEntity;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {

    List<BankBookEntity> findAllByUserId(Integer userId);
    Optional<BankBookEntity> findByUserIdAndNumberAndCurrency(Integer userId, String number, CurrencyEntity currency);
    void deleteAllByUserId(Integer userId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select bbe from BankBookEntity bbe where bbe.id = :id")
    Optional<BankBookEntity> lockById(@Param("id") Integer id);

    @Query("select bbe from BankBookEntity bbe where bbe.number = :number")
    @QueryHints(@QueryHint(name = HINT_CACHEABLE, value = "true"))
    Optional<BankBookEntity> findByNumber(@Param("number") String number);

}
