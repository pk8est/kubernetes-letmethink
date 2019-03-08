package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.PersistentVolumeClaimModel;
import com.pkest.web.service.warp.PersistentVolumeClaimWarp;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sPersistentVolumeClaimService extends K8sBaseService{

    PersistentVolumeClaim get(long cluserId, String name) throws HYException, K8sDriverException;

    PersistentVolumeClaim save(PersistentVolumeClaimModel model, PersistentVolumeClaim k8sModel)
            throws HYException, K8sDriverException;

    boolean delete(PersistentVolumeClaimModel model) throws HYException, K8sDriverException;

    PersistentVolumeClaim build(PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp) throws HYException;
}
