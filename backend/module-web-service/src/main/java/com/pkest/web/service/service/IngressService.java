package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.IngressMapper;
import com.pkest.repo.model.IngressModel;
import com.pkest.web.service.warp.IngressWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface IngressService extends BaseService<IngressModel, IngressMapper>{

    IngressModel create(IngressModel model, IngressWarp warp) throws HYException, K8sDriverException;

    IngressModel update(long id, IngressModel model, IngressWarp warp) throws HYException, K8sDriverException;

    void isUnique(@Nonnull IngressModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
