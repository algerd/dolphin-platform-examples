package ru.javafx.dolphin.security.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import ru.javafx.dolphin.security.core.entity.User;

@Validated
public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    void save(User user);
       
    boolean isUserExist(User user);
     
}
