package com.pkest.web.service.warp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = JsonDeserializer.None.class)
public class NamespaceWarp {
    private ObjectMetaWarp metadata;

}
