package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
public class AdminController {

    int count = 1;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleDao.getOne(2L)));
        userDao.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userDao.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody @Valid User user) {
        userDao.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll(HttpServletRequest request) {
        return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/{page}")
//    public String adminPage(ModelMap modelMap,
//                            Authentication authentication, @PathVariable(required = false) Integer page) {
//        User user = (User) authentication.getPrincipal();
//        Map<String, Object> map = new HashMap<>();
//        map.put("users", userDao.findAll(PageRequest.of(page, 4)));
//        map.put("user", user);
//        map.put("roles", user.getRoles());
//        map.put("newUser", new User());
//        map.put("currentPage", page);
//        modelMap.addAllAttributes(map);
//        return "admin";
//    }
//
//    @GetMapping("/add")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        return "add";
//    }

//    @PostMapping("/add")
//    public String registerNewUser(@ModelAttribute User user) {
////        if (userDao.findByEmail(user.getEmail()) == null) {
////            if (user.getPassword().equals(user.getPasswordRepeat())) {
////                user.setPassword(passwordEncoder.encode(user.getPassword()));
////                user.setRoles(Collections.singleton(roleDao.findById(1L).get()));
////                userDao.save(user);
////            }
////        }
//        return "redirect:/admin";
//    }

//    @GetMapping("/index")
//    public String edit() {
//        return "index";
//    }
//
//    @PostMapping("/edit/{page}")
//    public String edit(@ModelAttribute User user, @PathVariable Integer page) {
//        userDao.save(user);
//        return "redirect:/admin/" + page;
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Long id, HttpServletRequest request) {
//        userDao.deleteById(id);
//        return "redirect:/admin/" + 0;
//    }


}
