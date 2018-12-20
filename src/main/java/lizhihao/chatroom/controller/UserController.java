package lizhihao.chatroom.controller;

import lizhihao.chatroom.model.User;
import lizhihao.chatroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public String login(HttpServletResponse response, String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user!=null && user.getPassword().equals(password)){
            int avatar = userRepository.findByUserName(username).getAvatar();
            Cookie cookie = new Cookie("username", username);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            Cookie cookie1 = new Cookie("avatar", String.valueOf(avatar));
            cookie1.setPath("/");
            cookie1.setMaxAge(3600);
            response.addCookie(cookie1);
            return "redirect:/chat";
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout (HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("username")){
                    cookie.setMaxAge(0);
                }
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, String username, String password) {
        String msg = "注册成功";
        User user = userRepository.findByUserName(username);
        Random rand = new Random();
        User newUser = new User(username, password, username, rand.nextInt(10));
        userRepository.save(newUser);
        return "redirect:/login";
    }

    @PostMapping("/avatar")
    public  String changeAvatar(HttpServletRequest request, String avatar) {
        Cookie[] cookies =  request.getCookies();
        String username;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("username")){
                username = cookie.getValue();
                User user = userRepository.findByUserName(username);
                user.setAvatar(Integer.parseInt(avatar));
                userRepository.update(user);
            }
        }
        return "redirect:/chat";
    }
}
