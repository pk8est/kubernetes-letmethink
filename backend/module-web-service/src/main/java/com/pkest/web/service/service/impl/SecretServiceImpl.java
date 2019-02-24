package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.SecretMapper;
import com.pkest.repo.model.SecretModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sSecretService;
import com.pkest.web.service.service.SecretService;
import com.pkest.web.service.warp.SecretWarp;
import io.fabric8.kubernetes.api.model.Secret;
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
public class SecretServiceImpl extends BaseServiceImpl<SecretModel, SecretMapper> implements SecretService {

    @Resource
    private K8sSecretService k8sSecretService;

    @Override
    public SecretModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("配置["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public SecretModel create(SecretModel model, SecretWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Secret k8sModel = k8sSecretService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sSecretService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public SecretModel update(long id, SecretModel model, SecretWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        Secret k8sModel = k8sSecretService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sSecretService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull SecretModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        SecretModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sSecretService.delete(model);
    }

}
