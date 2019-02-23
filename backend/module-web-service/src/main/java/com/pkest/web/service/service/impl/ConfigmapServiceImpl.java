package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.ConfigmapMapper;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYObjMapper;
import com.pkest.web.service.k8s.K8sConfigmapService;
import com.pkest.web.service.k8s.K8sNamespaceService;
import com.pkest.web.service.service.ConfigmapService;
import com.pkest.web.service.service.NamespaceService;
import com.pkest.web.service.warp.ConfigmapWarp;
import com.pkest.web.service.warp.NamespaceWarp;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Namespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@Service
public class ConfigmapServiceImpl extends BaseServiceImpl<ConfigmapModel, ConfigmapMapper> implements ConfigmapService {

    @Resource
    private K8sConfigmapService k8sConfigmapService;

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapModel create(ConfigmapModel model, ConfigmapWarp warp) throws HYException, K8sDriverException {
        isUnique(model);

        ConfigMap k8sModel = k8sConfigmapService.build(model, warp);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeCreate(model);
        k8sConfigmapService.create(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public ConfigmapModel update(long id, ConfigmapModel model, ConfigmapWarp warp) throws HYException, K8sDriverException {
        isUnique(model);
        ConfigMap k8sModel = k8sConfigmapService.build(model, warp);
        getOrFail(id);
        model.setId(id);
        model.setYaml(HYObjMapper.toJson(k8sModel));
        GeUpdate(model);
        k8sConfigmapService.update(model, k8sModel);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull ConfigmapModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "namespaceId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

    @Override
    @Transactional(rollbackFor=Throwable.class)
    public boolean delete(long id) throws HYException, K8sDriverException{
        ConfigmapModel model = getOrFail(id);
        return GeDelete(id) == 1 &&  k8sConfigmapService.delete(model);
    }

}
