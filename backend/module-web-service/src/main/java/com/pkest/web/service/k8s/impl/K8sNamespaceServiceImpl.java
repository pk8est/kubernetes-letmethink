package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import org.springframework.stereotype.Service;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sNamespaceServiceImpl extends K8sBaseServiceImpl implements K8sNamespaceService {

    @Override
    public Namespace create(NamespaceModel model, Namespace namespace) throws HYException, K8sDriverException{
        return getKubeClient(model.getClusterId()).createNamespace(namespace);
    }

    @Override
    public Namespace update(NamespaceModel model, Namespace namespace) throws HYException, K8sDriverException{
        return getKubeClient(model.getClusterId()).replaceNamespace(namespace);
    }

    @Override
    public boolean delete(NamespaceModel model) throws HYException, K8sDriverException {
        return getKubeClient(model.getClusterId()).deleteNamespace(model.getName());
    }

    @Override
    public Namespace build(NamespaceModel model, NamespaceWarp warp){
         return new NamespaceBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .endMetadata()
                 .build();
    }

}
