package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.UserDto;
import com.ernestagyemang.productorderservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(UserDto userDto);

    User updateUser(UserDto userDto);

    void deleteUser(Long id);
}
