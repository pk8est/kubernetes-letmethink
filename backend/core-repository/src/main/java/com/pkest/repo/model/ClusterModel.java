package com.pkest.repo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@HYTable("lmt_cluster")
public class ClusterModel extends BaseModel {

    protected Long id;
    protected Long creatorUid;
    protected Date createdAt;
    protected Date updatedAt;
    protected String description;

    @JsonIgnore
    protected Integer deleteStatus;

    @JsonIgnore
    protected Date deletedAt;

    private String name;
    private String status;
    private String clusterMasterUrl;
    private String clusterUsername;
    private String clusterCertData;
    private String clusterCertKey;

}



