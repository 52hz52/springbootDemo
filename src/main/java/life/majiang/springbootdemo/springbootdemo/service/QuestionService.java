package life.majiang.springbootdemo.springbootdemo.service;

import life.majiang.springbootdemo.springbootdemo.dto.PaginationDTO;
import life.majiang.springbootdemo.springbootdemo.dto.QuestionDTO;
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


    public PaginationDTO list(Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1){
            page = 1;
        }
        if(page > paginationDTO.getTotalPage() ){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size*(page-1);

//      发布的问题集合  分页  limit
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();



        for (Question question : questions) {
         User user = userMapper.findByID(question.getCreator());
         QuestionDTO questionDto = new QuestionDTO();
//       把对象question的属性快速拷贝到questionDto
//       questionDto.setId(question.getId());
         BeanUtils.copyProperties(question,questionDto);
         questionDto.setUser(user);
//       添加到集合
         questionDTOList.add(questionDto);
        }
//      所有问题 用于分页
        paginationDTO.setQuestions(questionDTOList);




        return paginationDTO;
    }
}
