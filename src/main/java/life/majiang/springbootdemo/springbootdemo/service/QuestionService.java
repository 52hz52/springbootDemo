package life.majiang.springbootdemo.springbootdemo.service;

import life.majiang.springbootdemo.springbootdemo.dto.QuestionDto;
import life.majiang.springbootdemo.springbootdemo.mapper.QuestionMapper;
import life.majiang.springbootdemo.springbootdemo.mapper.UserMapper;
import life.majiang.springbootdemo.springbootdemo.model.Question;
import life.majiang.springbootdemo.springbootdemo.model.User;
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


    public List<QuestionDto> list() {

        List<Question> questions = questionMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
         User user = userMapper.findByID(question.getCreator());
         QuestionDto questionDto = new QuestionDto();
//       questionDto.setId(question.getId());
//       把对象question的属性快速拷贝到questionDto中
         BeanUtils.copyProperties(question,questionDto);
         questionDto.setUser(user);
         questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }
}
