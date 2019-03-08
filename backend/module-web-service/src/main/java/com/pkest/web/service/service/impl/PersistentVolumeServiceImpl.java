package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.PersistentVolumeMapper;
import com.pkest.repo.model.PersistentVolumeModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sPersistentVolumeService;
import com.pkest.web.service.service.PersistentVolumeService;
import com.pkest.web.service.warp.PersistentVolumeWarp;
import io.fabric8.kubernetes.api.model.PersistentVolume;
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
public class PersistentVolumeServiceImpl extends BaseServiceImpl<PersistentVolumeModel, PersistentVolumeMapper>
        implements PersistentVolumeService {

    @Resource
    private K8sPersistentVolumeService k8sPersistentVolumeService;

    @Override
    public PersistentVolumeModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("PV["+id+"]不存在!"));
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public PersistentVolumeModel create(PersistentVolumeModel model, PersistentVolumeWarp warp)
            throws HYException, K8sDriverException {
        isUnique(model);
        PersistentVolume k8sModel = k8sPersistentVolumeService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sPersistentVolumeService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public PersistentVolumeModel update(long id, PersistentVolumeModel model, PersistentVolumeWarp warp)
            throws HYException, K8sDriverException {
        isUnique(model);
        PersistentVolume k8sModel = k8sPersistentVolumeService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sPersistentVolumeService.save(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull PersistentVolumeModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        PersistentVolumeModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sPersistentVolumeService.delete(model);
    }

}
