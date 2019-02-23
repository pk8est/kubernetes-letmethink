package com.pkest.web.api.request;

import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Save;
import com.pkest.common.interfaces.Update;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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

