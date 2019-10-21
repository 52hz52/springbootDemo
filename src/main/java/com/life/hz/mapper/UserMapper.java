package com.life.hz.mapper;

import com.life.hz.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url,bio) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio} )")
    void insert(User user);

    @Select(" select * from user where token = #{token} ")
    User findByToken(@Param("token") String token);

    @Select(" select * from user where id = #{id} ")
    User findByID(@Param("id")Integer id);
}
