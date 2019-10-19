package life.majiang.springbootdemo.springbootdemo.controller;

import life.majiang.springbootdemo.springbootdemo.dto.QuestionDto;
import life.majiang.springbootdemo.springbootdemo.mapper.QuestionMapper;
import life.majiang.springbootdemo.springbootdemo.mapper.UserMapper;
import life.majiang.springbootdemo.springbootdemo.model.Question;
import life.majiang.springbootdemo.springbootdemo.model.User;
import life.majiang.springbootdemo.springbootdemo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    //   一个斜杠  表示根目录  就是默认访问页面
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0)
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.fingByToken(token);
                    if(null != user){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";

    }

}
