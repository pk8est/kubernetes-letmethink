package com.pkest.lib.myibatis;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by wuzhonggui on 2018/11/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYMybatisMapperRegistry extends MapperRegistry{

    private final static ThreadLocal<Class<?>> currentMapper = new ThreadLocal<>();
    private final static ThreadLocal<Field> currentMapperId = new ThreadLocal<>();

    private final Configuration config;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MapperProxyFactory<?>>();

    public HYMybatisMapperRegistry(Configuration config) {
        super(config);
        this.config = config;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface() && !type.isAnnotationPresent(NoRepositoryBean.class)) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new MapperProxyFactory<T>(type));
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MapperAnnotationBuilder parser = new HYMapperAnnotationBuilder(config, type);
                currentMapper.set(type);
                parser.parse();
                loadCompleted = true;
                currentMapper.set(null);
                currentMapperId.set(null);
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                    currentMapper.set(null);
                }
            }
        }
    }

    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }

    @Override
    public void addMappers(String packageName) {
        addMappers(packageName, Object.class);
    }

    public static Class<?> getCurrentMapper() {
        return currentMapper.get();
    }

    public static Field getCurrentMapperId() {
        return currentMapperId.get();
    }

    public static void setCurrentMapperId(Field id) {
        currentMapperId.set(id);
    }

}
