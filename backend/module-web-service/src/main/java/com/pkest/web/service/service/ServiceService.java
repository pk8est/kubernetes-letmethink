package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.ServiceMapper;
import com.pkest.repo.model.ServiceModel;
import com.pkest.web.service.warp.ServiceWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface ServiceService extends BaseService<ServiceModel, ServiceMapper>{

    ServiceModel create(ServiceModel model, ServiceWarp warp) throws HYException, K8sDriverException;

    ServiceModel update(long id, ServiceModel model, ServiceWarp warp) throws HYException, K8sDriverException;

    void isUnique(@Nonnull ServiceModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
