package com.life.hz.controller;

import com.life.hz.dto.CommentCreateDTO;
import com.life.hz.dto.CommentDTO;
import com.life.hz.dto.ResultDTO;
import com.life.hz.enums.CommentTypeEnum;
import com.life.hz.exception.CustomizeExceptionCode;
import com.life.hz.model.Comment;
import com.life.hz.model.User;
import com.life.hz.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody // 将对象序列化成json
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        //RequestBody 得到json数据

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeExceptionCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeExceptionCode.COMMENT_IS_FOUND);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L); // long 类型 要加 L
        comment.setCommentCount(0);

        commentService.insert(comment);
        return ResultDTO.okOf();
    }


    @ResponseBody // 将对象序列化成json
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id, Model model){

        List<CommentDTO> commentDTOList = commentService.ListByTargetId(id, CommentTypeEnum.COMMENT);

        ResultDTO resultDTO = ResultDTO.okOf(commentDTOList);



        return ResultDTO.okOf(commentDTOList);
    }


}
