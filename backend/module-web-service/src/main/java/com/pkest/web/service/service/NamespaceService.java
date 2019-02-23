package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.warp.NamespaceWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface NamespaceService extends BaseService<NamespaceModel, NamespaceMapper>{

    NamespaceModel create(NamespaceModel model, NamespaceWarp warp) throws HYException, K8sDriverException;

    NamespaceModel update(long id, NamespaceModel model, NamespaceWarp warp) throws HYException, K8sDriverException;

    void isUnique(@Nonnull NamespaceModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
