package ru.iteco.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iteco.account.model.dto.AddressDto;
import ru.iteco.account.model.entity.AddressEntity;
import ru.iteco.account.model.entity.UserEntity;
import ru.iteco.account.repository.AddressRepository;

@Component
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public UserEntity getUserByAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findByCountryAndCityAndStreetAndHome(addressDto.getCountry(), addressDto.getCity(),
                addressDto.getStreet(), addressDto.getHome())
                .orElseThrow(() -> new RuntimeException("Адрес не найден!"));
        return addressEntity.getUser();
    }

}
