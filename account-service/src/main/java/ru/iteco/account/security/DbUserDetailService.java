package ru.iteco.account.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.iteco.account.model.entity.UserAuthEntity;
import ru.iteco.account.repository.UserAuthRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DbUserDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthEntity user = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
