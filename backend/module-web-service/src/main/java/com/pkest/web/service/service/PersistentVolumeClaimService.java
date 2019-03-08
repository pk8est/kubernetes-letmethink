package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.PersistentVolumeClaimMapper;
import com.pkest.repo.model.PersistentVolumeClaimModel;
import com.pkest.web.service.warp.PersistentVolumeClaimWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface PersistentVolumeClaimService extends BaseService<PersistentVolumeClaimModel, PersistentVolumeClaimMapper>{

    PersistentVolumeClaimModel create(PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp)
            throws HYException, K8sDriverException;

    PersistentVolumeClaimModel update(long id, PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp)
            throws HYException, K8sDriverException;

    void isUnique(@Nonnull PersistentVolumeClaimModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
