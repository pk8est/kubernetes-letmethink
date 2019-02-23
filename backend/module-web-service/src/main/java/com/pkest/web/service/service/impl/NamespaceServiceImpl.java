package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.*;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYNumberUtils;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.service.ClusterService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.NamespaceWarp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.xml.stream.events.Namespace;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@Service
public class NamespaceServiceImpl extends BaseServiceImpl<NamespaceModel, NamespaceMapper> implements NamespaceService {

    @Resource
    private K8sNamespaceService k8sNamespaceService;

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public NamespaceModel create(NamespaceModel model, NamespaceWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        GeCreate(model);
        k8sNamespaceService.create(model, warp);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public NamespaceModel update(long id, NamespaceModel model, NamespaceWarp warp) throws HYException, K8sDriverException {
        model.setId(id);
        isUnique(model);
        getOrFail(id);
        GeUpdate(model);
        k8sNamespaceService.update(model, warp);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull NamespaceModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        NamespaceModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sNamespaceService.delete(model);
    }

}
