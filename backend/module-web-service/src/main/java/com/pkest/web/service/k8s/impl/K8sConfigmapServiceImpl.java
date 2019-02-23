package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sConfigmapService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.ConfigmapWarp;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sConfigmapServiceImpl extends K8sBaseServiceImpl implements K8sConfigmapService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public ConfigMap get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getConfigmap(name);
    }

    @Override
    public ConfigMap save(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createConfigmap(k8sModel);
        }else {
            return kubeClient.replaceConfigmap(k8sModel);

        }
    }

    @Override
    public boolean delete(ConfigmapModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deleteConfigmap(model.getName());
        }
        return true;
    }

    @Override
    public ConfigMap build(ConfigmapModel model, ConfigmapWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new ConfigMapBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withData(warp.getData())
                 .build();
    }

}
