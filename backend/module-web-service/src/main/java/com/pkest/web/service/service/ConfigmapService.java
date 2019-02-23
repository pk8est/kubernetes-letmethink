package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.ConfigmapMapper;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.ConfigmapModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.warp.ConfigmapWarp;
import com.pkest.web.service.warp.NamespaceWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface ConfigmapService extends BaseService<ConfigmapModel, ConfigmapMapper>{

    ConfigmapModel create(ConfigmapModel model, ConfigmapWarp warp) throws HYException, K8sDriverException;

    ConfigmapModel update(long id, ConfigmapModel model, ConfigmapWarp warp) throws HYException, K8sDriverException;

    void isUnique(@Nonnull ConfigmapModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
