package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.SecretModel;
import com.pkest.web.service.warp.SecretWarp;
import io.fabric8.kubernetes.api.model.Secret;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sSecretService extends K8sBaseService{

    Secret get(long cluserId, String name) throws HYException, K8sDriverException;

    Secret save(SecretModel model, Secret k8sModel) throws HYException, K8sDriverException;

    boolean delete(SecretModel model) throws HYException, K8sDriverException;

    Secret build(SecretModel model, SecretWarp warp) throws HYException;
}
