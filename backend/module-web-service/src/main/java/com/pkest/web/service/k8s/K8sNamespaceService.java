package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.Namespace;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sNamespaceService extends K8sBaseService{

    Namespace create(NamespaceModel model, Namespace namespace) throws HYException, K8sDriverException;

    Namespace update(NamespaceModel model, Namespace namespace) throws HYException, K8sDriverException;

    boolean delete(NamespaceModel model) throws HYException, K8sDriverException;

    Namespace build(NamespaceModel model, NamespaceWarp warp);
}
