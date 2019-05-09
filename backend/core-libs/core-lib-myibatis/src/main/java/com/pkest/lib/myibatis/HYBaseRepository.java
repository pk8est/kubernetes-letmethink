package com.pkest.lib.myibatis;


import com.pkest.lib.myibatis.annotation.AutoKeyProperty;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wuzhonggui on 2017/11/2.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface HYBaseRepository<T, K> {

    @Lang(HYLanguageDriver.class)
    @Insert({"<script>", "INSERT INTO @{table}(@{insertField}) VALUES (@{insertValue})", "</script>"})
    @Options(useGeneratedKeys=true)
    @AutoKeyProperty()
    K insert(T model);

    @Lang(HYLanguageDriver.class)
    @Insert({"<script>", "REPLACE INTO @{table}(@{insertField}) VALUES (@{insertValue})", "</script>"})
    @Options(useGeneratedKeys=true)
    @AutoKeyProperty()
    K replace(T model);

    @Lang(HYLanguageDriver.class)
    @Insert({"<script>", "INSERT INTO @{table}(@{batchInsertField}) VALUES @{batchInsertValue}", "</script>"})
    K batchInsert(@Param("list") List<T> list);

    @Lang(HYLanguageDriver.class)
    @Update({"<script>", "UPDATE @{table} SET @{updateSet} WHERE @{id} LIMIT 1", "</script>"})
    K update(T model);

    @Lang(HYLanguageDriver.class)
    @Update({"<script>", "UPDATE @{table} SET @{updateSet} WHERE @{id} LIMIT 1", "</script>"})
    K updateOne(T model);

    @Lang(HYLanguageDriver.class)
    @Update({"<script>", "UPDATE @{table} SET @{updateSet} WHERE @{id}", "</script>"})
    K updateAll(T model);

    @Lang(HYLanguageDriver.class)
    @Update({"<script>", "UPDATE @{table} SET @{updateSet} WHERE @{id}", "</script>"})
    K updateByMap(Map row);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{idField}=#{id} LIMIT 1", "</script>"})
    T find(K id);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{idField}=#{id} LIMIT 1", "</script>"})
    Map findMap(K id);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{idField}=#{id} LIMIT 1", "</script>"})
    T findWithField(@Param("id") K id, @Param("field") String field);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{idField}=#{id} LIMIT 1", "</script>"})
    Map findWithFieldMap(@Param("id") K id, @Param("field") String field);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{where} LIMIT 1", "</script>"})
    T findOne(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{where} LIMIT 1", "</script>"})
    Map findOneMap(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{where} LIMIT 1", "</script>"})
    T findOneWithField(@Param("where") QueryBuilder builder, @Param("field") String field);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{where} LIMIT 1", "</script>"})
    Map findOneWithFieldMap(@Param("where") QueryBuilder builder, @Param("field") String field);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{where}", "</script>"})
    List<T> findAll(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT @{field} FROM @{table} WHERE @{where}", "</script>"})
    List<Map> findAllMap(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{where}", "</script>"})
    List<T> findAllWithField(@Param("where") QueryBuilder builder, @Param("field") String field);

    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT ${field} FROM @{table} WHERE @{where}", "</script>"})
    List<Map> findAllWithFieldMap(@Param("where") QueryBuilder builder, @Param("field") String field);


    @Lang(HYLanguageDriver.class)
    @Select({"<script>", "SELECT count(1) FROM @{table} WHERE @{where}", "</script>"})
    K count(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Delete({"<script>", "DELETE FROM @{table} WHERE @{idField}=#{id} LIMIT 1", "</script>"})
    K delete(K id);

    @Lang(HYLanguageDriver.class)
    @Delete({"<script>", "DELETE FROM @{table} WHERE @{where} LIMIT 1", "</script>"})
    K deleteOne(@Param("where") QueryBuilder builder);

    @Lang(HYLanguageDriver.class)
    @Delete({"<script>", "DELETE FROM @{table} WHERE @{where}", "</script>"})
    K deleteAll(@Param("where") QueryBuilder builder);

}
