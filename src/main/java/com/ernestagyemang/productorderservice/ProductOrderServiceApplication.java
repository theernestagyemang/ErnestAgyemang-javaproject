package com.ernestagyemang.productorderservice;

import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProductOrderServiceApplication {

    public ProductOrderServiceApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductOrderServiceApplication.class, args);
    }

    private final PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = User.builder()
                    .name("Admin")
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .active(true)
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(user);
        };
    }

}
