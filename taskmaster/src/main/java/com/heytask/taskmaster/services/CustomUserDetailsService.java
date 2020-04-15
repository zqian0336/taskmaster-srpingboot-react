package com.heytask.taskmaster.services;

import com.heytask.taskmaster.repositories.UserRepository;
import com.heytask.taskmaster.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) new UsernameNotFoundException("User not found");
        return user;

    }

    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.getById(id);
        if(user == null) new UsernameNotFoundException("User not found");
        return user;

    }
}
