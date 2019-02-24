package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.IngressModel;
import com.pkest.web.service.warp.IngressWarp;
import io.fabric8.kubernetes.api.model.extensions.Ingress;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sIngressService extends K8sBaseService{

    Ingress get(long cluserId, String name) throws HYException, K8sDriverException;

    Ingress save(IngressModel model, Ingress k8sModel) throws HYException, K8sDriverException;

    boolean delete(IngressModel model) throws HYException, K8sDriverException;

    Ingress build(IngressModel model, IngressWarp warp) throws HYException;
}
