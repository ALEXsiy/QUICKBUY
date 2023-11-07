package com.baturin.QUICKbuy.services;

import com.baturin.QUICKbuy.models.User;
import com.baturin.QUICKbuy.models.enums.Role;
import com.baturin.QUICKbuy.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderService mailSenderService;
    public boolean createUser(User user){
        String email= user.getEmail();
        if(userRepository.findByEmail(email)!=null) return false;
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User with email: {}",email);
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message= String.format(
                    "Hello, %s!\n" +
                            "Welcome to our shop. Please," +
                            " visit next link: http://localhost:8082/activate/%s",
                    user.getName(), user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code",message);
        }

        return true;
    }

    public List<User> list(){
        return userRepository.findAll();
    }


    public void banUser(Long id) {
        User user= userRepository.findById(id).orElse(null);
        if(user!=null){
            if(user.isActive()){
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
            else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }

        }
        userRepository.save(user);

    }
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public boolean activateUser(String code) {
        User user=userRepository.findByActivationCode(code);
        if(user==null) return false;
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;

    }


}
