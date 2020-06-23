package com.descartes.qlf.service;

import com.descartes.qlf.model.Adresse;
import com.descartes.qlf.model.user;
import com.descartes.qlf.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }
    public user findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
