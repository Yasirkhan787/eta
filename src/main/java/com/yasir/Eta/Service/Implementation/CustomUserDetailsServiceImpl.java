package com.yasir.Eta.Service.Implementation;


import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Repositories.UserRepository;
import com.yasir.Eta.Service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        //log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByEmail(username);
        if(user == null){
            //log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        //log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

}

