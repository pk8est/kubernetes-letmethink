package com.pkest.repo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
public class BaseModel {
    protected Long id ;
    protected Long creatorUid;
    protected Date createdAt;
    protected Date updatedAt;
    protected String description;

    @JsonIgnore
    protected Integer deleteStatus;

    @JsonIgnore
    protected Date deletedAt;

}
