package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.repo.model.ServiceModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sServiceService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.ServiceWarp;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@org.springframework.stereotype.Service
public class K8sServiceServiceImpl extends K8sBaseServiceImpl implements K8sServiceService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public Service get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getService(name);
    }

    @Override
    public Service save(ServiceModel model, Service k8sModel) throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createService(k8sModel);
        }else {
            return kubeClient.replaceService(model.getName(), k8sModel);
        }
    }

    @Override
    public boolean delete(ServiceModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deleteSecret(model.getName());
        }
        return true;
    }

    @Override
    public Service build(ServiceModel model, ServiceWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new ServiceBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withSpec(warp.getSpec())
                 .build();
    }

}
