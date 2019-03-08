package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.PersistentVolumeClaimMapper;
import com.pkest.repo.model.PersistentVolumeClaimModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sPersistentVolumeClaimService;
import com.pkest.web.service.service.PersistentVolumeClaimService;
import com.pkest.web.service.warp.PersistentVolumeClaimWarp;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
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
public class PersistentVolumeClaimServiceImpl extends BaseServiceImpl<PersistentVolumeClaimModel, PersistentVolumeClaimMapper>
        implements PersistentVolumeClaimService {

    @Resource
    private K8sPersistentVolumeClaimService k8sPersistentVolumeClaimService;

    @Override
    public PersistentVolumeClaimModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("PV["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public PersistentVolumeClaimModel create(PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp)
            throws HYException, K8sDriverException {
        isUnique(model);
        PersistentVolumeClaim k8sModel = k8sPersistentVolumeClaimService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sPersistentVolumeClaimService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public PersistentVolumeClaimModel update(long id, PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp)
            throws HYException, K8sDriverException {
        isUnique(model);
        PersistentVolumeClaim k8sModel = k8sPersistentVolumeClaimService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sPersistentVolumeClaimService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull PersistentVolumeClaimModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        PersistentVolumeClaimModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sPersistentVolumeClaimService.delete(model);
    }

}
