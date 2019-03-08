package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.repo.model.PersistentVolumeClaimModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sPersistentVolumeClaimService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.PersistentVolumeClaimWarp;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sPersistentVolumeClaimServiceImpl extends K8sBaseServiceImpl implements K8sPersistentVolumeClaimService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public PersistentVolumeClaim get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getPersistentVolumeClaim(name);
    }

    @Override
    public PersistentVolumeClaim save(PersistentVolumeClaimModel model, PersistentVolumeClaim k8sModel)
            throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createPersistentVolumeClaim(k8sModel);
        }else {
            return kubeClient.replacePersistentVolumeClaim(model.getName(), k8sModel);

        }
    }

    @Override
    public boolean delete(PersistentVolumeClaimModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deletePersistentVolumeClaim(model.getName());
        }
        return true;
    }

    @Override
    public PersistentVolumeClaim build(PersistentVolumeClaimModel model, PersistentVolumeClaimWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new PersistentVolumeClaimBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withSpec(warp.getSpec())
                 .build();
    }

}
