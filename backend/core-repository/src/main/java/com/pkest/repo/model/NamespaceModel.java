package com.pkest.repo.model;

import com.pkest.lib.myibatis.annotation.HYTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@HYTable("lmt_namespace")
public class NamespaceModel extends BaseModel {

    private String name ;
    private String alias;
    private String yaml ;

    private String type ;
    private String status ;

}



