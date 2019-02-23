package com.pkest.web.service.k8s.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.common.collect.ImmutableMap;
import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.GsonUtils;
import com.pkest.web.service.service.ClusterService;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Namespace;
import com.pkest.web.service.k8s.K8sNamespaceService;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpecBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Service
public class K8sNamespaceServiceImpl extends K8sBaseServiceImpl implements K8sNamespaceService {

    @Override
    public Namespace create(NamespaceModel model, NamespaceWarp warp) throws HYException, K8sDriverException{
        return getKubeClient(model.getClusterId()).createNamespace(build(model, warp));
    }

    public Namespace build(NamespaceModel model, NamespaceWarp warp){

        return new NamespaceBuilder()
                .withNewMetadata()
                .withName(model.getName())
                .endMetadata()
                .withNewSpec()
                .withFinalizers("kubernetes")
                .endSpec()
                .build();
       /* Namespace namespace = new Namespace();
        BeanUtils.copyProperties(warp, namespace);
        warp.getMetadata().setName(model.getName());
        return namespace;*/
    }

}
