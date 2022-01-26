package ru.iteco.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.iteco.account.model.dto.BankBookDto;
import ru.iteco.account.model.entity.BankBookEntity;

@Mapper(componentModel = "spring")
public interface BankBookMapper {

    @Mapping(target = "currency", source = "currency.name")
    BankBookDto mapToDto(BankBookEntity bankBookEntity);
    @Mapping(target = "currency.name", source = "currency")
    BankBookEntity mapToEntity(BankBookDto bankBookDto);

}
