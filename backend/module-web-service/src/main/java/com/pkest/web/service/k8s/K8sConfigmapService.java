package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.warp.ConfigmapWarp;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Namespace;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sConfigmapService extends K8sBaseService{

    ConfigMap create(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException;

    ConfigMap update(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException;

    boolean delete(ConfigmapModel model) throws HYException, K8sDriverException;

    ConfigMap build(ConfigmapModel model, ConfigmapWarp warp);
}
