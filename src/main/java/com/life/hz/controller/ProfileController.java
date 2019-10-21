package com.life.hz.controller;

import com.life.hz.dto.PaginationDTO;
import com.life.hz.model.User;
import com.life.hz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request, Model model,
                          @RequestParam(name = "page" ,defaultValue = "1") Integer page){
//      每页显示数量
        Integer size = 2;
        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            return "redirect/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PaginationDTO paginationDTO = questionService.listUserByID(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }

}
