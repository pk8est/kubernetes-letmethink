package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.repo.mapper.ClusterMapper;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.ClusterModel;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.web.service.service.ClusterService;
import com.pkest.web.service.service.NamespaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * Created by wuzhonggui on 2019/2/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Slf4j
@Service
public class ClusterServiceImpl extends BaseServiceImpl<ClusterModel, ClusterMapper> implements ClusterService {

    @Override
    public ClusterModel getOrFail(long id) throws HYException{
        return GeFind(id).orElseThrow(new RecordNotFoundException("集群["+id+"]不存在!"));
    }

    @Override
    public ClusterModel create(ClusterModel model) throws HYException{
        isUnique(model);
        GeCreate(model);
        return getOrFail(model.getId());
    }

    @Override
    public ClusterModel update(long id, ClusterModel model) throws HYException{
        model.setId(id);
        isUnique(model);
        getOrFail(id);
        GeUpdate(model);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull ClusterModel model) throws HYException {
        if(!GeUnique(model, new String[]{"name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

}
