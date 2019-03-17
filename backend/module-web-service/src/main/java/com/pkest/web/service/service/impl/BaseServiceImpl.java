package com.pkest.web.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.pkest.common.bean.PageInfo;
import com.pkest.common.exception.HYException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.lib.myibatis.HYMybatisReflectUtil;
import com.pkest.lib.myibatis.QueryBuilder;
import com.pkest.lib.myibatis.annotation.HYColumn;
import com.pkest.lib.myibatis.enums.QueryOperator;
import com.pkest.repo.mapper.BaseMapper;
import com.pkest.repo.model.BaseModel;
import com.pkest.util.HYPropertyUtils;
import com.pkest.util.HYStringUtils;
import com.pkest.web.service.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wuzhonggui on 2018/11/27.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class BaseServiceImpl<T extends BaseModel, K extends BaseMapper> implements BaseService<T, K> {

    final static String DELETE_FIELD_KEY = "deleteStatus";
    final static String DELETEAT_FIELD_KEY = "deletedAt";
    final static String ID_KEY = "id";
    private Map<String, String> tableColumnMaps = null;

    K mapper;

    @Autowired
    public void setMapper(K mapper){
        this.mapper = mapper;
    }

    @Override
    public Class<T> getModelClass(){
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Map<String, String> getTableColumns(){
        if(tableColumnMaps == null){
            synchronized (this){
                if(tableColumnMaps == null){
                    this.tableColumnMaps = new HashMap<>();
                    for(Field field: getModelClass().getDeclaredFields()){
                        if(Modifier.isStatic(field.getModifiers())){
                            continue;
                        }
                        HYColumn column = field.getAnnotation(HYColumn.class);
                        if(column == null || (column.invisible() == false)){
                            tableColumnMaps.put(field.getName(), HYMybatisReflectUtil.getColumnName(field));
                        }
                    }
                }
            }
        }
        return this.tableColumnMaps;
    }

    @Override
    public String getTableColumn(String name){
        if(getTableColumns().containsKey(name)){
            return getTableColumns().get(name);
        }
        return null;
    }

    @Override
    public K getMapper(){
        return mapper;
    }

    @Override
    public T get(long id){
        return GeFind(id).orElse(null);
    }

    @Override
    public T getOrNew(long id){
        return GeFind(id).orElse(BeanUtils.instantiate(getModelClass()));
    }

    @Override
    public T getOrFail(long id) throws HYException {
        return GeFind(id).orElseThrow(new RecordNotFoundException(
                String.format("%s: id is %s model not fount!", getModelClass().getName(), id)
        ));
    }

    @Override
    public Optional<T> GeFind(Long id){
        return GeFind(id, true);
    }

    @Override
    public Optional<T> GeFind(Long id, boolean filterDeleteStatus){
        return GeFind(new CompareBuilder(ID_KEY, id), filterDeleteStatus);
    }

    @Override
    public Optional<T> GeFind(QueryBuilder builder){
        return GeFind(builder, true);
    }

    @Override
    public Optional<T> GeFind(QueryBuilder builder, boolean filterDeleteStatus){
        if(filterDeleteStatus) builder.filter(getTableColumn(DELETE_FIELD_KEY), 0);
        return Optional.ofNullable((T)getMapper().findOne(builder));
    }

    @Override
    public List<T> GeFindAll(QueryBuilder builder){
        return GeFindAll(builder, true);
    }

    @Override
    public List<T> GeFindAll(QueryBuilder builder, boolean filterDeleteStatus){
        if(filterDeleteStatus) builder.filter(getTableColumn(DELETE_FIELD_KEY), 0);
        return getMapper().findAll(builder);
    }

    @Override
    public PageInfo<T> GePagination(Pageable pageable){
        return GePagination(new CompareBuilder(), pageable);
    }

    @Override
    public PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable){
        return GePagination(builder, pageable, true);
    }

    @Override
    public PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, boolean filterDeleteStatus){
        return GePagination(builder, pageable, filterDeleteStatus, null);
    }

    @Override
    public PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, List<String> sortableFields){
        return GePagination(builder, pageable, true, sortableFields);
    }

    @Override
    public PageInfo<T> GePagination(QueryBuilder builder, Pageable pageable, boolean filterDeleteStatus, List<String> sortableFields){
        if(filterDeleteStatus) builder.filter(HYStringUtils.toUnderline(DELETE_FIELD_KEY), 0);
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), true);
        if(sortableFields == null){
            builder.setSortable(pageable.getSort(), getTableColumns());
        }else{
            builder.setSortable(pageable.getSort(), getTableColumns().entrySet().stream()
                    .filter(e -> sortableFields.contains(e.getKey()))
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
        }
        return new PageInfo(getMapper().findAll(builder));
    }

    @Override
    public Long GeCreate(BaseModel model){
        getMapper().insert(model);
        return model.id();
    }

    @Override
    public Long GeUpdate(BaseModel model){
        return (Long) getMapper().update(model);
    }

    @Override
    public Long GeSave(BaseModel model) {
        Long id = model.id();
        if(id == null){
            GeCreate(model);
        }else{
            GeUpdate(model);
        }
        return id;
    }

    @Override
    public Long GeDelete(Long id){
        return GeDelete(id, true);
    }

    @Override
    public Long GeDelete(Long id, boolean virtual){
        if(virtual){
            return (Long)getMapper().updateOne(ImmutableMap.of(ID_KEY, id, DELETE_FIELD_KEY,1, DELETEAT_FIELD_KEY, new Date()));
        }else{
            return (Long)getMapper().delete(id);
        }
    }

    @Override
    public boolean GeUnique(Object model, String[] fields){
        return GeUnique(model, ID_KEY, fields, true);
    }

    @Override
    public boolean GeUnique(Object model, String[] fields, boolean filterDeleteStatus){
        return GeUnique(model, ID_KEY, fields, filterDeleteStatus);
    }

    @Override
    public boolean GeUnique(Object model, String id, String[] fields, boolean filterDeleteStatus){
        CompareBuilder builder = new CompareBuilder();
        for (String field: fields){
            builder.filter(HYStringUtils.toUnderline(field), HYPropertyUtils.getProperty(model, HYStringUtils.toCamel(field), ""));
        }
        builder.compare(id, QueryOperator.NEQ, HYPropertyUtils.getProperty(model, id, 0));
        return GeFind(builder, filterDeleteStatus).orElse(null) == null ? true : false;
    }

}
