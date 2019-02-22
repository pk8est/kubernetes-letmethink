package com.pkest.repo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pkest.util.HYPropertyUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
public class BaseModel {

    public String pk(){
        return "id";
    }

    public Long id(){
        return HYPropertyUtils.getProperty(this, pk(), 0L);
    }

}
