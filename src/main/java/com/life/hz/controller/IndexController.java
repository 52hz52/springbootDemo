package com.life.hz.controller;

import com.life.hz.dto.PaginationDTO;
import com.life.hz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    //   一个斜杠  表示根目录  就是默认访问页面
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page" ,defaultValue = "1")Integer page) {
        Integer size = 10 ;
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        if(pagination.getData().size() > 0 ){
            return "index";
        }
        else {
            return "index";
        }



    }

}
