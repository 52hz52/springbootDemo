package com.life.hz.dto;

import com.life.hz.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentator;
    private String content;
    private User user;
}
