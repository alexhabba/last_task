package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.repository.UserDao;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

//	@GetMapping
//    public String loginPage(Authentication authentication, Model model) {
//        User user = (User) authentication.getPrincipal();
//        model.addAttribute("user", user);
//        return "user";
//    }


        @GetMapping
    public String adminPage(ModelMap modelMap,
                            Authentication authentication, @PathVariable(required = false) Integer page) {
        User user = (User) authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("users", userDao.findAll());
        map.put("user", user);
        map.put("roles", user.getRoles());
        map.put("newUser", new User());
        map.put("currentPage", page);
        modelMap.addAllAttributes(map);
        return "index";
    }
}