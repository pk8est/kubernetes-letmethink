package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.NamespaceModel;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface NamespaceService extends BaseService<NamespaceModel, NamespaceMapper>{

    NamespaceModel create(NamespaceModel model) throws HYException;

    NamespaceModel update(long id, NamespaceModel model) throws HYException;

    void isUnique(@Nonnull NamespaceModel model) throws HYException;
}
