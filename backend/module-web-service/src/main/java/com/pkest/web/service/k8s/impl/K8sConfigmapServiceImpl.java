package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sConfigmapService;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.ConfigmapWarp;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
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
    public ConfigMap create(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException{
        return getKubeClient(model.getClusterId()).createConfigmap(k8sModel);
    }

    @Override
    public ConfigMap update(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException{
        return getKubeClient(model.getClusterId()).replaceConfigmap(k8sModel);
    }

    @Override
    public boolean delete(ConfigmapModel model) throws HYException, K8sDriverException {
        return getKubeClient(model.getClusterId()).deleteConfigmap(model.getName());
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
