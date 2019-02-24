package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.ServiceMapper;
import com.pkest.repo.model.ServiceModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sServiceService;
import com.pkest.web.service.service.ServiceService;
import com.pkest.web.service.warp.ServiceWarp;
import io.fabric8.kubernetes.api.model.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@org.springframework.stereotype.Service
public class ServiceServiceImpl extends BaseServiceImpl<ServiceModel, ServiceMapper> implements ServiceService {

    @Resource
    private K8sServiceService k8sServiceService;

    @Override
    public ServiceModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("负载均衡["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ServiceModel create(ServiceModel model, ServiceWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Service k8sModel = k8sServiceService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sServiceService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ServiceModel update(long id, ServiceModel model, ServiceWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Service k8sModel = k8sServiceService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sServiceService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull ServiceModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        ServiceModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sServiceService.delete(model);
    }

}
