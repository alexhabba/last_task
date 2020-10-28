package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Role;
import web.model.User;
import web.repository.UserDao;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public String loginPage(ModelMap modelMap,
                            Authentication authentication, @PathVariable(required = false) Integer page) {
        User user = authentication == null ? null : (User) authentication.getPrincipal();
        if (user != null) {
            String roleUser = user.getRoles().stream().map(Role::getRole).collect(Collectors.joining(" "));
            Map<String, Object> map = new HashMap<>();
            map.put("users", userDao.findAll());
//            map.put("users", userDao.findAll(PageRequest.of(page, 4)));
            map.put("user", user);
            map.put("roles", roleUser.toString());
            map.put("newUser", new User());
            map.put("currentPage", page);
            modelMap.addAllAttributes(map);
            return "admin";
        }
        modelMap.addAttribute("user", user);
        return "login";
    }

    @GetMapping("access-denied")
    public String accessDeniedGet() {
        return "access-denied";
    }
}
