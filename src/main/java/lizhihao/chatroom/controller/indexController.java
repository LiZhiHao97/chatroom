package lizhihao.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/chat")
    public String chatroom(Model model, HttpServletRequest request) {
        Boolean isLogin = false;
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("username")){
                    isLogin = true;
                }
            }
        }
        if(!isLogin) {
            return "redirect:/login";
        }
        return "chat";
    }
}
