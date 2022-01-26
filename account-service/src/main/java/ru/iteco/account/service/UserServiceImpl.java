package ru.iteco.account.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.account.model.dto.AddressDto;
import ru.iteco.account.model.dto.UserDto;
import ru.iteco.account.model.entity.AddressEntity;
import ru.iteco.account.model.entity.UserEntity;
import ru.iteco.account.model.exception.UserNotFoundException;
import ru.iteco.account.repository.AddressRepository;
import ru.iteco.account.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!", id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = mapToEntity(userDto);
        return mapToDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден!", userDto.getId()));

        AddressDto addressDto = userDto.getAddress();
        AddressEntity address = userEntity.getAddress();
        if (address != null && addressDto != null) {
            address.setCountry(addressDto.getCountry());
            address.setCity(addressDto.getCity());
            address.setStreet(addressDto.getStreet());
            address.setHome(addressDto.getHome());
            addressRepository.save(address);
        }

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());

        return mapToDto(userRepository.save(userEntity));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .address(mapToDto(userEntity.getAddress()))
                .build();
    }

    private UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setAddress(mapToEntity(userDto.getAddress()));
        return userEntity;
    }

    private AddressDto mapToDto(AddressEntity addressEntity) {
        if (addressEntity == null) return null;
        return AddressDto.builder()
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .home(addressEntity.getHome())
                .build();
    }

    private AddressEntity mapToEntity(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHome(addressDto.getHome());
        return addressEntity;
    }

}
