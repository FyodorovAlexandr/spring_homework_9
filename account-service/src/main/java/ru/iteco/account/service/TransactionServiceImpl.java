package ru.iteco.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.account.model.dto.BankBookDto;
import ru.iteco.account.model.dto.Status;
import ru.iteco.account.model.dto.UserDto;
import ru.iteco.account.model.entity.BankBookEntity;
import ru.iteco.account.model.entity.TransactionEntity;
import ru.iteco.account.model.exception.BankBookNotFoundException;
import ru.iteco.account.model.exception.CurrencyNotEqualsException;
import ru.iteco.account.repository.BankBookRepository;
import ru.iteco.account.repository.StatusRepository;
import ru.iteco.account.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class TransactionServiceImpl implements TransactionService {

    private final BankBookRepository bankBookRepository;
    private final BankBookService bankBookService;
    private final UserService userService;
    private final StatusRepository statusRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Boolean transferBetweenBankBook(BankBookDto sourceBankBook, BankBookDto targetBankBook, BigDecimal amount) {
        BankBookEntity source = bankBookRepository.findById(sourceBankBook.getId()).orElseThrow(() -> new BankBookNotFoundException("Счет не найден"));
        BankBookEntity target = bankBookRepository.findById(targetBankBook.getId()).orElseThrow(() -> new BankBookNotFoundException("Счет не найден"));
        if (!source.getCurrency().equals(target.getCurrency())) {
            throw new CurrencyNotEqualsException("Счета не совпадают!");
        }

        bankBookRepository.findById(sourceBankBook.getId()); //id = 1

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setSourceBankBook(source);
        transactionEntity.setTargerBankBook(target);
        transactionEntity.setAmount(amount);
        transactionEntity.setInitiationDate(LocalDateTime.now());
        transactionEntity.setStatus(statusRepository.findByName(Status.PROCESSING.getStatus()));

        if (source.getAmount().compareTo(amount) < 0) {
            transactionEntity.setStatus(statusRepository.findByName(Status.DECLINED.getStatus()));
            transactionEntity.setCompletionDate(LocalDateTime.now());
            transactionRepository.save(transactionEntity);
            return false;
        }

        source.setAmount(source.getAmount().subtract(amount));
        target.setAmount(target.getAmount().add(amount));

        bankBookRepository.save(source);
        bankBookRepository.save(target);

        transactionEntity.setStatus(statusRepository.findByName(Status.SUCCESSFUL.getStatus()));
        transactionEntity.setCompletionDate(LocalDateTime.now());
        transactionRepository.save(transactionEntity);
        return true;
    }

    @Override
    @Transactional
    public Boolean transferBetweenUser(UserDto sourceUser, UserDto targetUser, BigDecimal amount, String currency) {
        List<BankBookDto> sourceBankBooks = bankBookService.findByUserId(sourceUser.getId());
        List<BankBookDto> targetBankBooks = bankBookService.findByUserId(targetUser.getId());
        BankBookDto source = sourceBankBooks.stream()
                .filter(bankBookDto -> bankBookDto.getCurrency().equals(currency))
                .findAny()
                .orElseThrow();
        BankBookDto target = targetBankBooks.stream()
                .filter(bankBookDto -> bankBookDto.getCurrency().equals(currency))
                .findAny()
                .orElseThrow();
        return transferBetweenBankBook(source, target, amount);
    }
}
