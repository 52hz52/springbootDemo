package life.majiang.springbootdemo.springbootdemo.controller;

import life.majiang.springbootdemo.springbootdemo.mapper.UserMapper;
import life.majiang.springbootdemo.springbootdemo.model.User;
import life.majiang.springbootdemo.springbootdemo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action, HttpServletRequest request, Model model){

        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0){
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.fingByToken(token);
                    if(null != user){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            return "indns";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }

}
