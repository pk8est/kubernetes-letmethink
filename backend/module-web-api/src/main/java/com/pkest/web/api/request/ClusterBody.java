package com.pkest.web.api.request;

import com.pkest.common.interfaces.Save;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 22:41
 */
@Data
public class ClusterBody extends BaseBody{

    @NotNull(groups = Save.class)
    private String name;

    @NotNull(groups = Save.class)
    private String clusterMasterUrl;
    @NotNull(groups = Save.class)
    private String clusterUsername;
    @NotNull(groups = Save.class)
    private String clusterCertData;
    @NotNull(groups = Save.class)
    private String clusterCertKey;

    private String type;
    private String status;
    private String description;



}

