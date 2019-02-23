package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.KubeClient;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ClusterModel;

import javax.validation.constraints.NotNull;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sBaseService {

    KubeClient getKubeClient(Long clusterId) throws HYException, K8sDriverException;

    KubeClient getKubeClient(@NotNull ClusterModel cluster) throws K8sDriverException;
}
