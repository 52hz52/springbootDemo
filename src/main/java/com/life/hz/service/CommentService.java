package com.life.hz.service;

import com.life.hz.enums.CommentTypeEnum;
import com.life.hz.exception.CustomizeException;
import com.life.hz.exception.CustomizeExceptionCode;
import com.life.hz.mapper.CommentMapper;
import com.life.hz.mapper.QuestionExtMapper;
import com.life.hz.mapper.QuestionMapper;
import com.life.hz.model.Comment;
import com.life.hz.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

//  事务  错了就不执行这个方法..
    @Transactional
    public void insert(Comment comment){

        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeExceptionCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeExceptionCode.TYPE_PARAM_WORNG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType() ){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null ){
                throw new CustomizeException(CustomizeExceptionCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null ){
                throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
