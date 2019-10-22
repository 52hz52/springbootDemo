package com.life.hz.mapper;

import com.life.hz.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface QuestionMapper {

    @Insert(" insert into question (title,desciption,gmt_create,gmt_modified,creator,tag) values ( #{title},#{desciption},#{gmtCreate},#{gmtModified},#{creator},#{tag} ) ")
    void create(Question question);

    @Select(" select * from question limit #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select(" select count(1) from question ")
    Integer count();

    @Select(" select * from question where creator = #{userID} limit #{offset},#{size} ")
    List<Question> listByUserId(@Param(value = "userID")Integer userID,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("  select count(1) from question where creator = #{userID} ")
    Integer countByUserId(@Param(value = "userID")Integer userID);

    @Select("  select * from question where id = #{id} ")
    Question getById(@Param("id") Integer id);

    @Update(" update question set title=#{title}, desciption=#{desciption}, tag=#{tag}, gmt_modified=#{gmtModified} where id=#{id} ")
    void update(Question question);
}
