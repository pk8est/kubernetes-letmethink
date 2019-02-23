package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.repo.mapper.ConfigmapGroupMapper;
import com.pkest.repo.mapper.ConfigmapSetMapper;
import com.pkest.repo.model.ConfigmapGroupModel;
import com.pkest.repo.model.ConfigmapSetModel;
import com.pkest.web.service.service.ConfigmapGroupService;
import com.pkest.web.service.service.ConfigmapSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@Service
public class ConfigmapGroupServiceImpl extends BaseServiceImpl<ConfigmapGroupModel, ConfigmapGroupMapper> implements ConfigmapGroupService {


    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapGroupModel create(ConfigmapGroupModel model) throws HYException {
        isUnique(model);
        GeCreate(model);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapGroupModel update(long id, ConfigmapGroupModel model) throws HYException {
        isUnique(model);
        getOrFail(id);
        model.setId(id);
        GeUpdate(model);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull ConfigmapGroupModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "setId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException{
        getOrFail(id);
        return GeDelete(id) == 1;
    }

}
