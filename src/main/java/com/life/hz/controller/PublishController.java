package com.life.hz.controller;

import com.life.hz.cache.TagCache;
import com.life.hz.dto.QuestionDTO;
import com.life.hz.model.Question;
import com.life.hz.model.User;
import com.life.hz.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
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
                                @PathVariable(name = "id",required=false)Long id,
                                Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("desciption",questionDTO.getDesciption());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",id);
        model.addAttribute("tags",TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required=false)String title,
            @RequestParam(value = "desciption",required=false)String desciption,
            @RequestParam(value = "tag",required=false)String tag,
            @RequestParam(value = "id",required = false)Long id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("desciption",desciption);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());

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
        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return"publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (null == user ){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        //转换小写去空格
        String tagLower =  tag.toLowerCase().replaceAll(" ","");
        question.setTag(tagLower);
        question.setDesciption(desciption);
        question.setCreator(user.getId());
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);
        question.setId(id);


//       添加一个问题
        questionService.createOrUpdate(question);

        return "redirect:/";

    }


}
