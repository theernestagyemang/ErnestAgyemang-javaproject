package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.MyUserDetailsDto;
import com.ernestagyemang.productorderservice.dto.UserInput;
import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.exceptions.Duplicate409Exception;
import com.ernestagyemang.productorderservice.exceptions.InValidEmailException;
import com.ernestagyemang.productorderservice.exceptions.NotAuthorizedException;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.UserRepository;
import com.ernestagyemang.productorderservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));
    }

    @Override
    public User createUser(UserInput userInput) {
        validateEmailFormat(userInput.getEmail());
        userRepository.findByEmail(userInput.getEmail()).ifPresent(u -> {
            throw new Duplicate409Exception("User with email " + userInput.getEmail() + " already exists");
        });
        return userRepository.save(User.builder()
                .name(userInput.getName())
                .email(userInput.getEmail())
                .role(UserRole.valueOf(userInput.getRole().toUpperCase()))
                .active(true)
                .password(passwordEncoder.encode(userInput.getPassword()))
                .build());

    }

    @Override
    public User updateUser(UserInput userInput, Principal principal) {
        if (currentUser(principal).getRole().equals(UserRole.ROLE_USER)) {
            if (!Objects.equals(currentUser(principal).getId(), userInput.getId())) {
                throw new NotAuthorizedException("You do not have permission to update another user");
            }
        }
        validateEmailFormat(userInput.getEmail());
        User user = userRepository.findById(userInput.getId()).orElseThrow(() -> new NotFoundException("User with id " + userInput.getId() + " does not exist"));
        user.setName(userInput.getName());
        user.setEmail(userInput.getEmail());
        user.setActive(true);
        user.setRole(UserRole.valueOf(userInput.getRole().toUpperCase()));
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted";
    }

    private void validateEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InValidEmailException("Invalid email format");
        }
    }

    public User currentUser(Principal principal) {
        try {
            MyUserDetailsDto userDetails = (MyUserDetailsDto) ((Authentication) principal).getPrincipal();
            return userDetails.getUser();
        } catch (NotFoundException n) {
            throw new NotFoundException("Invalid email format");
        }
    }
}
