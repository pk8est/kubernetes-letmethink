package com.pkest.web.service.service;

import com.pkest.common.bean.PageInfo;
import com.pkest.common.exception.HYException;
import com.pkest.lib.myibatis.QueryBuilder;
import com.pkest.repo.mapper.BaseMapper;
import com.pkest.repo.model.BaseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wuzhonggui on 2018/11/27.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface BaseService<T extends BaseModel, K extends BaseMapper>{

    Class<T> getModelClass();

    Map<String, String> getTableColumns();

    String getTableColumn(String name);

    K getMapper();

    T get(long id);

    T getOrNew(long id);

    T getOrFail(long id) throws HYException;

    Optional<T> GeFind(Long id);

    Optional<T> GeFind(Long id, boolean deleteStatus);

    Optional<T> GeFind(QueryBuilder builder);

    Optional<T> GeFind(QueryBuilder builder, boolean filterDeleteStatus);

    List<T> GeFindAll(QueryBuilder builder);

    List<T> GeFindAll(QueryBuilder builder, boolean filterDeleteStatus);

    PageInfo<T> GePagination(Pageable pageable);

    PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable);

    PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, boolean filterDeleteStatus);

    PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, List<String> sortableFields);

    PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, boolean filterDeleteStatus, List<String> sortableFields);

    Long GeCreate(BaseModel model);

    Long GeUpdate(BaseModel model);

    Long GeSave(BaseModel model);

    Long GeDelete(Long id);

    Long GeDelete(Long id, boolean virtual);

    boolean GeUnique(Object model, String[] fields);

    boolean GeUnique(Object model, String[] fields, boolean filterDeleteStatus);

    boolean GeUnique(Object model, String id, String[] fields, boolean filterDeleteStatus);

}
