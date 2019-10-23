package com.life.hz.service;

import com.life.hz.dto.PaginationDTO;
import com.life.hz.dto.QuestionDTO;
import com.life.hz.exception.CustomizeException;
import com.life.hz.exception.CustomizeExceptionCode;
import com.life.hz.mapper.QuestionMapper;
import com.life.hz.mapper.UserMapper;
import com.life.hz.model.Question;
import com.life.hz.model.QuestionExample;
import com.life.hz.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

        if( totalCount%size == 0 ){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1 ;
        }

        if(page<1){
            page = 1;
        }
        if(page > totalPage ){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size*(page-1);

//      发布的问题集合  分页  limit
        List<Question> questions = questionMapper.
                selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
         User user = userMapper.selectByPrimaryKey(question.getCreator());

         QuestionDTO questionDto = new QuestionDTO();
//       把对象question的属性快速拷贝到questionDto
//       questionDto.setId(question.getId());
         BeanUtils.copyProperties(question,questionDto);
         System.out.println(user);
         questionDto.setUser(user);
//       添加到集合
         questionDTOList.add(questionDto);
        }
//      所有问题 用于分页
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }


    public PaginationDTO listUserByID(Integer userID, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userID);
        Integer totalCount = (int)questionMapper.countByExample(example);

        if( totalCount%size == 0 ){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1 ;
        }

        if(page<1){
            page = 1;
        }
        if(page > totalPage ){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size*(page-1);

//      发布的问题集合  分页  limit
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userID);

        List<Question> questions = questionMapper.
                selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());

            QuestionDTO questionDto = new QuestionDTO();
//       把对象question的属性快速拷贝到questionDto
//       questionDto.setId(question.getId());
            BeanUtils.copyProperties(question,questionDto);
            System.out.println(user);
            questionDto.setUser(user);
//       添加到集合
            questionDTOList.add(questionDto);
        }
//      所有问题 用于分页
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;


    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        if(question == null ){
            throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if( question.getId() == null ){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
      //      question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDesciption(question.getDesciption());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());

            int updateRow = questionMapper.updateByExampleSelective(updateQuestion, example);
//          没有更新成功
            if(updateRow != 1){
                throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
            }

        }
    }
}
