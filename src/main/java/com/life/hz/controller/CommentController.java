package com.life.hz.controller;

import com.life.hz.dto.CommentDTO;
import com.life.hz.dto.ResultDTO;
import com.life.hz.exception.CustomizeExceptionCode;
import com.life.hz.mapper.CommentMapper;
import com.life.hz.model.Comment;
import com.life.hz.model.User;
import com.life.hz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody // 将对象序列化成json
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        //RequestBody 得到json数据

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeExceptionCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L); // long 类型 要加 L
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

}
