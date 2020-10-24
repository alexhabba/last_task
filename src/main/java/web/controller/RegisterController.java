package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;

import java.util.Collections;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;

    @Autowired
    public RegisterController(UserDao userDao,
                              PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @GetMapping()
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute User user, Authentication authentication) {
        if (userDao.findByEmail(user.getEmail()) == null && authentication == null) {
            if (user.getPassword().equals(user.getPasswordRepeat())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(Collections.singletonList(roleDao.getOne(2L)));
                userDao.save(user);
                return "redirect:/login";
            }
        } else {
            if (userDao.findByEmail(user.getEmail()) == null) {
                if (user.getPassword().equals(user.getPasswordRepeat())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setRoles(Collections.singletonList(roleDao.getOne(2L)));
                    userDao.save(user);
                    return "redirect:/admin/" + 0;
                }
            }
        }
        return "redirect:/";
    }
}
