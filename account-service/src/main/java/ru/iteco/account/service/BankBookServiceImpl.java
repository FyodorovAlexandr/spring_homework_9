package ru.iteco.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import ru.iteco.account.mapper.BankBookMapper;
import ru.iteco.account.model.dto.BankBookDto;
import ru.iteco.account.model.entity.BankBookEntity;
import ru.iteco.account.model.entity.CurrencyEntity;
import ru.iteco.account.model.exception.BankBookNotFoundException;
import ru.iteco.account.model.exception.BankBookNumberCannotBeModifiedException;
import ru.iteco.account.model.exception.BankBookWithCurrencyAlreadyHaveException;
import ru.iteco.account.repository.BankBookRepository;
import ru.iteco.account.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Validated
class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final CurrencyRepository currencyRepository;
    private final BankBookMapper bankBookMapper;

    @Override
    public BankBookDto findById(Integer id) {
        return bankBookRepository.findById(id)
                .map(bankBookMapper::mapToDto)
                .orElseThrow(() -> new BankBookNotFoundException("Cчет не найден!"));
    }

    @Override
    public List<BankBookDto> findByUserId(Integer userId) {
        List<BankBookDto> bankBookDtos = bankBookRepository.findAllByUserId(userId).stream()
                .map(bankBookMapper::mapToDto)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bankBookDtos)) {
            throw new BankBookNotFoundException("Для данного пользователя нет счетов");
        }
        return bankBookDtos;
    }

    @Override
    public BankBookDto create(BankBookDto bankBookDto) {
        CurrencyEntity currency = currencyRepository.findByName(bankBookDto.getCurrency());
        Optional<BankBookEntity> bankBookEntityOpt = bankBookRepository.findByUserIdAndNumberAndCurrency(
                bankBookDto.getUserId(),
                bankBookDto.getNumber(),
                currency
        );
        if (bankBookEntityOpt.isPresent()) {
            throw new BankBookWithCurrencyAlreadyHaveException("Счет с данной валютой уже имеется!");
        }
        BankBookEntity bankBookEntity = bankBookMapper.mapToEntity(bankBookDto);
        bankBookEntity.setCurrency(currency);
        return bankBookMapper.mapToDto(
                bankBookRepository.save(bankBookEntity)
        );
    }

    @Override
    public BankBookDto update(BankBookDto bankBookDto) {
        BankBookEntity bankBookEntity = bankBookRepository.findById(bankBookDto.getId())
                .orElseThrow(() -> new BankBookNotFoundException("Лицевой счет не найден!"));

        if (!bankBookEntity.getNumber().equals(bankBookDto.getNumber())) {
            throw new BankBookNumberCannotBeModifiedException("Номер лицевого счета менять нельзя!");
        }

        CurrencyEntity currency = currencyRepository.findByName(bankBookDto.getCurrency());

        bankBookEntity = bankBookMapper.mapToEntity(bankBookDto);
        bankBookEntity.setCurrency(currency);
        return bankBookMapper.mapToDto(
                bankBookRepository.save(bankBookEntity)
        );
    }

    @Override
    public void delete(Integer id) {
        bankBookRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        bankBookRepository.deleteAllByUserId(userId);
    }

}
