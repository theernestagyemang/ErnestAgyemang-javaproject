package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.UserDto;
import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.exceptions.Duplicate409Exception;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.UserRepository;
import com.ernestagyemang.productorderservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new Duplicate409Exception("User with email " + userDto.getEmail() + " already exists"));
        return userRepository.save(User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(UserRole.valueOf(userDto.getRole().toUpperCase()))
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build());
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("User with id " + userDto.getId() + " does not exist"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(UserRole.valueOf(userDto.getRole().toUpperCase()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        userRepository.deleteById(id);
    }
}
