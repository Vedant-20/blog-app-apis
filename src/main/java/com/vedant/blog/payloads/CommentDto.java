package com.vedant.blog.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Integer id;
    private String content;
    private Date addedDate;
}
