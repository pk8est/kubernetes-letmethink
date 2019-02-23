package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.repo.mapper.ConfigmapGroupMapper;
import com.pkest.repo.model.ConfigmapGroupModel;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface ConfigmapGroupService extends BaseService<ConfigmapGroupModel, ConfigmapGroupMapper>{

    ConfigmapGroupModel create(ConfigmapGroupModel model) throws HYException;

    ConfigmapGroupModel update(long id, ConfigmapGroupModel model) throws HYException;

    void isUnique(@Nonnull ConfigmapGroupModel model) throws HYException;

    boolean delete(long id) throws HYException;
}
