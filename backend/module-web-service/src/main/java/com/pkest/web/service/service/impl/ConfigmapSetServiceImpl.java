package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.ConfigmapSetMapper;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.ConfigmapSetModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.service.ConfigmapSetService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.Namespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@Service
public class ConfigmapSetServiceImpl extends BaseServiceImpl<ConfigmapSetModel, ConfigmapSetMapper> implements ConfigmapSetService {

    @Override
    public ConfigmapSetModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("配置集合["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapSetModel create(ConfigmapSetModel model) throws HYException {
        isUnique(model);
        GeCreate(model);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapSetModel update(long id, ConfigmapSetModel model) throws HYException {
        isUnique(model);
        getOrFail(id);
        model.setId(id);
        GeUpdate(model);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull ConfigmapSetModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
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
