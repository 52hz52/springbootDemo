package life.majiang.springbootdemo.springbootdemo.mapper;

import life.majiang.springbootdemo.springbootdemo.dto.QuestionDto;
import life.majiang.springbootdemo.springbootdemo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionMapper {

    @Insert(" insert into question (title,desciption,gmt_create,gmt_modified,creator,tag) values ( #{title},#{desciption},#{gmtCreate},#{gmtModified},#{creator},#{tag} ) ")
    void create(Question question);

    @Select(" select * from question ")
    List<Question> list();
}
