package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.PersistentVolumeMapper;
import com.pkest.repo.model.PersistentVolumeModel;
import com.pkest.web.service.warp.PersistentVolumeWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface PersistentVolumeService extends BaseService<PersistentVolumeModel, PersistentVolumeMapper>{

    PersistentVolumeModel create(PersistentVolumeModel model, PersistentVolumeWarp warp)
            throws HYException, K8sDriverException;

    PersistentVolumeModel update(long id, PersistentVolumeModel model, PersistentVolumeWarp warp)
            throws HYException, K8sDriverException;

    void isUnique(@Nonnull PersistentVolumeModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
