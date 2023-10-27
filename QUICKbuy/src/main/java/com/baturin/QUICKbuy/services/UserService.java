package com.baturin.QUICKbuy.services;

import com.baturin.QUICKbuy.models.User;
import com.baturin.QUICKbuy.models.enums.Role;
import com.baturin.QUICKbuy.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean createUser(User user){
        String email= user.getEmail();
        if(userRepository.findByEmail(email)!=null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User with email: {}",email);
        userRepository.save(user);
        return true;
    }
}
