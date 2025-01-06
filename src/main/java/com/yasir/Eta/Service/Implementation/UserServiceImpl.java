package com.yasir.Eta.Service.Implementation;

import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Entities.UserRole;
import com.yasir.Eta.Exception.InvalidCredentialsException;
import com.yasir.Eta.Exception.UserAlreadyExistsException;
import com.yasir.Eta.Exception.UserNotFoundException;
import com.yasir.Eta.Repositories.UserRepository;
import com.yasir.Eta.Requests.LoginRequest;
import com.yasir.Eta.Requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpUser(RegisterRequest request) {
        // Check if email already exists
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Set<UserRole> roles = new HashSet<>();

        // Create a new User entity
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setRoles(roles);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        // Save user to the repository
        userRepository.save(newUser);

        return newUser;
    }

    // Login User
    public void signInUser(LoginRequest request) {
        // Check if email exists
        User existingUser = userRepository.findByEmail(request.getUsername());
        if (existingUser == null) {
            throw new UserNotFoundException("User with email " + request.getUsername() + " does not exist.");
        }

        // Check if password is correct
        if (!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public List<User> getUsers() {
        return List.of();
    }

    public void updateUser(RegisterRequest user) {
    }
}
