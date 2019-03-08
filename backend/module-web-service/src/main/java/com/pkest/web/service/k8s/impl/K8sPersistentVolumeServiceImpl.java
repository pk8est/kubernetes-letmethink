package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.repo.model.PersistentVolumeModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sPersistentVolumeService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.PersistentVolumeWarp;
import io.fabric8.kubernetes.api.model.PersistentVolume;
import io.fabric8.kubernetes.api.model.PersistentVolumeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sPersistentVolumeServiceImpl extends K8sBaseServiceImpl implements K8sPersistentVolumeService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public PersistentVolume get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getPersistentVolume(name);
    }

    @Override
    public PersistentVolume save(PersistentVolumeModel model, PersistentVolume k8sModel)
            throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createPersistentVolume(k8sModel);
        }else {
            return kubeClient.replacePersistentVolume(model.getName(), k8sModel);

        }
    }

    @Override
    public boolean delete(PersistentVolumeModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deletePersistentVolume(model.getName());
        }
        return true;
    }

    @Override
    public PersistentVolume build(PersistentVolumeModel model, PersistentVolumeWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new PersistentVolumeBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withSpec(warp.getSpec())
                 .build();
    }

}
