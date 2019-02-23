package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.KubeClientImpl;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ClusterModel;
import com.pkest.web.service.k8s.K8sBaseService;
import com.pkest.web.service.service.ClusterService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
@Slf4j
public class K8sBaseServiceImpl implements K8sBaseService {

    @Resource
    private ClusterService clusterService;

    @Override
    public KubeClient getKubeClient(Long clusterId) throws HYException, K8sDriverException {
        return getKubeClient(clusterService.getOrFail(clusterId));
    }

    @Override
    public KubeClient getKubeClient(@NotNull ClusterModel cluster) throws K8sDriverException {

        KubeClient kubeClient = KubeClientImpl.build(KubeClientImpl.configDefaultBuilder()
                    .withMasterUrl(cluster.getClusterMasterUrl())
                    .withUsername(cluster.getClusterUsername())
                    .withClientCertData(cluster.getClusterCertData())
                    .withClientKeyData(cluster.getClusterCertKey())
                    .build());
        log.info("[{}]创建kube客户端[{}]成功", cluster.getName(), cluster.getClusterMasterUrl());
        return kubeClient;
    }


}
