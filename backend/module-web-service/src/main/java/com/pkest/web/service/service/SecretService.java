package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import com.pkest.repo.mapper.SecretMapper;
import com.pkest.repo.model.SecretModel;
import com.pkest.web.service.warp.SecretWarp;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface SecretService extends BaseService<SecretModel, SecretMapper>{

    SecretModel create(SecretModel model, SecretWarp warp) throws HYException, K8sDriverException;

    SecretModel update(long id, SecretModel model, SecretWarp warp) throws HYException, K8sDriverException;

    void isUnique(@Nonnull SecretModel model) throws HYException;

    boolean delete(long id) throws HYException, K8sDriverException;
}
