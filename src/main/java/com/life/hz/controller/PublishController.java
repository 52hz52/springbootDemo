package com.life.hz.controller;

import com.life.hz.mapper.QuestionMapper;
import com.life.hz.model.Question;
import com.life.hz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

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

        User user = (User) request.getSession().getAttribute("user");

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
