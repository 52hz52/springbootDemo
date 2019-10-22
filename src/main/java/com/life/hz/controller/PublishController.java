package com.life.hz.controller;

import com.life.hz.dto.QuestionDTO;
import com.life.hz.model.Question;
import com.life.hz.model.User;
import com.life.hz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String editQuestion( @RequestParam(value = "title",required=false)String title,
                                @RequestParam(value = "desciption",required=false)String desciption,
                                @RequestParam(value = "tag",required=false)String tag,
                                @PathVariable(name = "id",required=false)Integer id,
                                Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("desciption",questionDTO.getDesciption());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",id);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required=false)String title,
            @RequestParam(value = "desciption",required=false)String desciption,
            @RequestParam(value = "tag",required=false)String tag,
            @RequestParam(value = "id",required = false)Integer id,
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
        if(id!=null){
            question.setId(id);
        }
//       添加一个问题
        questionService.createOrUpdate(question);
//        questionMapper.create(question);
        return "redirect:/";

    }


}
