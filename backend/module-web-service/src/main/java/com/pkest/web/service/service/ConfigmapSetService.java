package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.repo.mapper.ConfigmapSetMapper;
import com.pkest.repo.model.ConfigmapSetModel;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface ConfigmapSetService extends BaseService<ConfigmapSetModel, ConfigmapSetMapper>{

    ConfigmapSetModel create(ConfigmapSetModel model) throws HYException;

    ConfigmapSetModel update(long id, ConfigmapSetModel model) throws HYException;

    void isUnique(@Nonnull ConfigmapSetModel model) throws HYException;

    boolean delete(long id) throws HYException;
}
