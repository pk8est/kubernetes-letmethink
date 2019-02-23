package com.pkest.web.service.service;

import com.pkest.common.exception.HYException;
import com.pkest.repo.mapper.ClusterMapper;
import com.pkest.repo.model.ClusterModel;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface ClusterService extends BaseService<ClusterModel, ClusterMapper>{

    ClusterModel create(ClusterModel model) throws HYException;

    ClusterModel update(long id, ClusterModel model) throws HYException;

    void isUnique(@Nonnull ClusterModel model) throws HYException;
}
