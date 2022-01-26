package ru.iteco.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.account.model.UserAuthDto;
import ru.iteco.account.model.entity.UserAuthEntity;
import ru.iteco.account.repository.UserAuthRepository;

@Component
@RequiredArgsConstructor
class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String create(UserAuthDto userAuthDto) {
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUsername(userAuthDto.getLogin());
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        return userAuthRepository.save(userAuthEntity).getUsername();
    }
}
