package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.repo.model.SecretModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sSecretService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.SecretWarp;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sSecretServiceImpl extends K8sBaseServiceImpl implements K8sSecretService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public Secret get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getSecret(name);
    }

    @Override
    public Secret save(SecretModel model, Secret k8sModel) throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createSecret(k8sModel);
        }else {
            return kubeClient.replaceSecret(k8sModel);

        }
    }

    @Override
    public boolean delete(SecretModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deleteSecret(model.getName());
        }
        return true;
    }

    @Override
    public Secret build(SecretModel model, SecretWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new SecretBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withType(warp.getType())
                 .withData(warp.getData())
                 .withStringData(warp.getStringData())
                 .build();
    }

}
