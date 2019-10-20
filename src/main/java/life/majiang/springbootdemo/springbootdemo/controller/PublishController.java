package life.majiang.springbootdemo.springbootdemo.controller;

import life.majiang.springbootdemo.springbootdemo.mapper.QuestionMapper;
import life.majiang.springbootdemo.springbootdemo.mapper.UserMapper;
import life.majiang.springbootdemo.springbootdemo.model.Question;
import life.majiang.springbootdemo.springbootdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required=false)String title,
            @RequestParam(value = "desciption",required=false)String desciption,
            @RequestParam(value = "tag",required=false)String tag,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("desciption",desciption);
        model.addAttribute("tag",tag);

        if(null == title || "" == title){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(null == desciption || "" == desciption){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(null == tag || "" == tag){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length!=0)
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
        if (null == user ){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDesciption(desciption);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
//       添加一个问题
        questionMapper.create(question);
        return "redirect:/";

    }


}
