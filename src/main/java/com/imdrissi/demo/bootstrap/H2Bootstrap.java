package com.imdrissi.demo.bootstrap;

import com.imdrissi.demo.security.User;
import com.imdrissi.demo.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile({"dev", "test"})
@Component
@Slf4j
public class H2Bootstrap implements CommandLineRunner {

  @Autowired UserRepository userRepository;

  @Override
  public void run(String... strings) throws Exception {
    log.info("bootstrap users");
    User admin = new User("admin", new BCryptPasswordEncoder().encode("admin"));
    admin.setAuthorities(Arrays.asList("ADMIN", "MANAGER"));
    userRepository.save(admin);

    User manager = new User("manager", new BCryptPasswordEncoder().encode("manager"));
    manager.setAuthorities(Arrays.asList("MANAGER"));
    userRepository.save(manager);

    User user = new User("user", new BCryptPasswordEncoder().encode("user"));
    user.setAuthorities(Arrays.asList("USER"));
    userRepository.save(user);
  }
}
