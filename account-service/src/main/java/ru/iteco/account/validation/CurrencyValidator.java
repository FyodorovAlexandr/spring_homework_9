package ru.iteco.account.validation;

import ru.iteco.account.repository.CurrencyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class CurrencyValidator implements ConstraintValidator<Currency, String> {

    private final CurrencyRepository currencyRepository;

    CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        return currencyRepository.findByName(currency) != null;
    }

}
