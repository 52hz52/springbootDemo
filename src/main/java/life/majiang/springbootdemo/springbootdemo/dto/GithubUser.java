package life.majiang.springbootdemo.springbootdemo.dto;

import lombok.Data;
/* 自动会生成 set get  toString 方法 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
