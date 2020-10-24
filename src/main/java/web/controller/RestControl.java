package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.User;
import web.repository.UserDao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class RestControl {

    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/editffff")
    public ResponseEntity<User> findOne(HttpServletRequest request, HttpRequestResponseHolder responseHolder, Long id) {
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        final Cookie[] cookies = request.getCookies();
        User user = userDao.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
