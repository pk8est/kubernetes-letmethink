package com.pkest.web.service.service.impl;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.*;
import com.pkest.repo.mapper.NamespaceMapper;
import com.pkest.repo.model.NamespaceModel;
import com.pkest.util.HYNumberUtils;
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
public class NamespaceServiceImpl extends BaseServiceImpl<NamespaceModel, NamespaceMapper> implements NamespaceService {

    @Override
    public NamespaceModel create(NamespaceModel model) throws HYException{
        isUnique(model);
        GeCreate(model);
        return getOrFail(model.getId());
    }

    @Override
    public NamespaceModel update(long id, NamespaceModel model) throws HYException{
        model.setId(id);
        isUnique(model);
        getOrFail(id);
        GeUpdate(model);
        return getOrFail(model.getId());
    }

    @Override
    public void isUnique(@Nonnull NamespaceModel model) throws HYException {
        if(!GeUnique(model, new String[]{"clusterId", "name"})){
            throw new HYKnownException(ResultCode.SERVER_ERROR.message("[" + model.getName() + "]已经存在!"));
        }
    }

}
