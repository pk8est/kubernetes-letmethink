package com.pkest.web.api.request;

import com.pkest.common.interfaces.Insert;
import com.pkest.common.interfaces.Update;
import com.pkest.web.service.warp.ConfigmapWarp;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 22:41
 */
@Data
public class ConfigmapBody extends BaseBody{

    @NotNull(groups = Insert.class)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private String name;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long clusterId;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long namespaceId;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long setId;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long groupId;

    private String alias;
    private String version;
    private String type;
    private String status;
    private String description;

    private ConfigmapWarp yaml;

}

