package com.pkest.web.api.request;

import com.pkest.common.interfaces.Insert;
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
public class ConfigmapSetBody extends BaseBody{

    @NotNull(groups = Insert.class)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private String name;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long clusterId;

    @Min(0)
    @Null(groups = Update.class, message = "{valid.notCanUpdate}")
    private Long namespaceId;

    private String alias;
    private String type;
    private String status;
    private String description;

}

