package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.PersistentVolumeModel;
import com.pkest.web.service.warp.PersistentVolumeWarp;
import io.fabric8.kubernetes.api.model.PersistentVolume;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sPersistentVolumeService extends K8sBaseService{

    PersistentVolume get(long cluserId, String name) throws HYException, K8sDriverException;

    PersistentVolume save(PersistentVolumeModel model, PersistentVolume k8sModel) throws HYException, K8sDriverException;

    boolean delete(PersistentVolumeModel model) throws HYException, K8sDriverException;

    PersistentVolume build(PersistentVolumeModel model, PersistentVolumeWarp warp) throws HYException;
}
