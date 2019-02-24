package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.IngressMapper;
import com.pkest.repo.model.IngressModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sIngressService;
import com.pkest.web.service.service.IngressService;
import com.pkest.web.service.warp.IngressWarp;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
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
public class IngressServiceImpl extends BaseServiceImpl<IngressModel, IngressMapper> implements IngressService {

    @Resource
    private K8sIngressService k8sIngressService;

    @Override
    public IngressModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("服务["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public IngressModel create(IngressModel model, IngressWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Ingress k8sModel = k8sIngressService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sIngressService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public IngressModel update(long id, IngressModel model, IngressWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Ingress k8sModel = k8sIngressService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sIngressService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull IngressModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        IngressModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sIngressService.delete(model);
    }

}
