package com.pkest.web.service.k8s.impl;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.Namespace;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
        Namespace namespace = new Namespace();
        BeanUtils.copyProperties(warp, namespace);
        warp.getMetadata().setName(model.getName());
        return namespace;
    }

}
