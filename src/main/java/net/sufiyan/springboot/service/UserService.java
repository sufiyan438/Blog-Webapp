package net.sufiyan.springboot.service;

import net.sufiyan.springboot.dto.RegistrationDto;
import net.sufiyan.springboot.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
