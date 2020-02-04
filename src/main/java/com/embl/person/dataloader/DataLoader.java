package com.embl.person.dataloader;

import com.embl.person.entity.Roles;
import com.embl.person.entity.User;
import com.embl.person.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(final UserRepository userRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
        final User user = new User();
        user.setUsername("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        user.setFirstName("John");
        user.setLastName("Smith");

        final Roles role = new Roles();
        role.setUser(user);
        role.setRole("ROLE_ADMIN");
        final Set<Roles> rolesList = new HashSet<>();
        rolesList.add(role);
        user.setRoles(rolesList);

        userRepository.save(user);

        final User user1 = new User();
        user1.setUsername("user");
        user1.setPassword(bCryptPasswordEncoder.encode("user"));
        user1.setFirstName("Harsh");
        user1.setLastName("Vardhan");

        final Roles role1 = new Roles();
        role1.setUser(user1);
        role1.setRole("ROLE_USER");
        final Set<Roles> rolesList1 = new HashSet<>();
        rolesList1.add(role1);
        user1.setRoles(rolesList1);

        userRepository.save(user1);
    }
}
