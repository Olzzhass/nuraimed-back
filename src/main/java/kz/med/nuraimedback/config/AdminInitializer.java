package kz.med.nuraimedback.config;

import kz.med.nuraimedback.model.Role;
import kz.med.nuraimedback.model.User;
import kz.med.nuraimedback.repository.RoleRepository;
import kz.med.nuraimedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        // Создаем роль ADMIN если её нет
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    Role savedRole = roleRepository.save(role);
                    log.info("Created ADMIN role with ID: {}", savedRole.getId());
                    return savedRole;
                });

        // Создаем роль USER если её нет
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    Role savedRole = roleRepository.save(role);
                    log.info("Created USER role with ID: {}", savedRole.getId());
                    return savedRole;
                });

        // Проверяем, существует ли пользователь admin
        if (!userRepository.findByUsername("admin").isPresent()) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .roles(Set.of(adminRole))
                    .build();

            User savedUser = userRepository.save(adminUser);
            log.info("Created admin user with ID: {} and username: {}", savedUser.getId(), savedUser.getUsername());
            log.info("Admin user password: admin123");
        } else {
            log.info("Admin user already exists");
        }
    }
}
