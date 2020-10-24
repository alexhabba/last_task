package web;

import org.springframework.security.crypto.password.PasswordEncoder;
import web.model.Role;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class TestMappingApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestMappingApplication.class, args);

        RoleDao roleRepo = context.getBean(RoleDao.class);
        UserDao userRepo = context.getBean(UserDao.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        List<Role> roles = Arrays.asList(role1, role2);

        roleRepo.save(role2);
        roleRepo.save(role1);

        User user1 = new User("ivan", "ivanov", 25, "a", passwordEncoder.encode(""), roles);
        user1.setRoles(roles);
        userRepo.save(user1);
        for (int i = 1; i <= 1; i++) {
            User user2 = new User("vasy" + i, "pupkin", 21, "u", passwordEncoder.encode(""), Collections.singletonList(role1));
            user2.setRoles(Collections.singletonList(role1));
            userRepo.save(user2);
        }


    }

}
