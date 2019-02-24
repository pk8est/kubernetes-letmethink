package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ServiceModel;
import com.pkest.web.service.warp.ServiceWarp;
import io.fabric8.kubernetes.api.model.Service;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sServiceService extends K8sBaseService{

    Service get(long cluserId, String name) throws HYException, K8sDriverException;

    Service save(ServiceModel model, Service k8sModel) throws HYException, K8sDriverException;

    boolean delete(ServiceModel model) throws HYException, K8sDriverException;

    Service build(ServiceModel model, ServiceWarp warp) throws HYException;
}
