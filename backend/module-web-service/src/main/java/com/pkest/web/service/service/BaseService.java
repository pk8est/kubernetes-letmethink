package com.pkest.web.service.service;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.exception.HYException;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.repo.mapper.BaseMapper;
import com.pkest.repo.model.BaseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by wuzhonggui on 2018/11/27.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface BaseService<T extends BaseModel, K extends BaseMapper>{

    K getMapper();

    T get(long id);

    T getOrNew(long id);

    T getOrFail(long id) throws HYException;

    Optional<T> GeFind(Long id);

    Optional<T> GeFind(Long id, boolean deleteStatus);

    Optional<T> GeFind(CompareBuilder compareBuilder);

    Optional<T> GeFind(CompareBuilder compareBuilder, boolean filterDeleteStatus);

    List<T> GeFindAll(CompareBuilder compareBuilder);

    List<T> GeFindAll(CompareBuilder compareBuilder, boolean filterDeleteStatus);

    PageInfo<T> GePagination(Pageable pageable);

    PageInfo<T> GePagination(CompareBuilder compareBuilder, Pageable pageable);

    PageInfo<T> GePagination(CompareBuilder compareBuilder, Pageable pageable, boolean filterDeleteStatus);

    Long GeCreate(BaseModel model);

    Long GeUpdate(BaseModel model);

    Long GeSave(BaseModel model);

    Long GeDelete(Long id);

    Long GeDelete(Long id, boolean virtual);

    boolean GeUnique(Object model, String[] fields);

    boolean GeUnique(Object model, String[] fields, boolean filterDeleteStatus);

    boolean GeUnique(Object model, String id, String[] fields, boolean filterDeleteStatus);

}
