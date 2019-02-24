package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.IngressModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.web.service.k8s.K8sIngressService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.IngressWarp;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.api.model.extensions.IngressBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sIngressServiceImpl extends K8sBaseServiceImpl implements K8sIngressService {

    @Resource
    private NamespaceService namespaceService;

    @Override
    public Ingress get(long cluserId, String name) throws HYException, K8sDriverException{
        return getKubeClient(cluserId).getIngress(name);
    }

    @Override
    public Ingress save(IngressModel model, Ingress k8sModel) throws HYException, K8sDriverException{
        KubeClient kubeClient = getKubeClient(model.getClusterId());
        if(get(model.getClusterId(), model.getName()) == null){
            return kubeClient.createIngress(k8sModel);
        }else {
            return kubeClient.replaceIngress(model.getName(), k8sModel);

        }
    }

    @Override
    public boolean delete(IngressModel model) throws HYException, K8sDriverException {
        if(get(model.getClusterId(), model.getName()) != null){
            return getKubeClient(model.getClusterId()).deleteIngress(model.getName());
        }
        return true;
    }

    @Override
    public Ingress build(IngressModel model, IngressWarp warp) throws HYException {
        NamespaceModel namespaceModel = namespaceService.getOrFail(model.getNamespaceId());
         return new IngressBuilder()
                 .withNewMetadata()
                 .addToLabels(HYPropertyUtils.get(warp, "metadata.labels"))
                 .withName(model.getName())
                 .withNamespace(namespaceModel.getName())
                 .endMetadata()
                 .withSpec(warp.getSpec())
                 .build();
    }

}
