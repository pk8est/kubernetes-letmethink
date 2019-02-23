package com.pkest.web.service.k8s;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.web.service.warp.ConfigmapWarp;
import io.fabric8.kubernetes.api.model.ConfigMap;

/**
 * @author 360733598@qq.com
 * @date 2019/2/23 10:05
 */
public interface K8sConfigmapService extends K8sBaseService{

    ConfigMap get(long cluserId, String name) throws HYException, K8sDriverException;

    ConfigMap save(ConfigmapModel model, ConfigMap k8sModel) throws HYException, K8sDriverException;

    boolean delete(ConfigmapModel model) throws HYException, K8sDriverException;

    ConfigMap build(ConfigmapModel model, ConfigmapWarp warp) throws HYException;
}
