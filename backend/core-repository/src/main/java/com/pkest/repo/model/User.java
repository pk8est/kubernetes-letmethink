package com.pkest.repo.model;

import com.pkest.lib.myibatis.annotation.HYTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@HYTable("system_user")
public class User extends Base {
    private Long id ;
    private String name ;
    private Long uid ;
    private String udb ;
    private Integer status ;
    private Integer vip ;
    private Long creatorUid;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}

