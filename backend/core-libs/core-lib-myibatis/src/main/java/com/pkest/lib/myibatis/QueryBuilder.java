package com.pkest.lib.myibatis;

import com.pkest.lib.myibatis.enums.QueryFieldValueType;
import com.pkest.lib.myibatis.enums.QueryOperator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 360733598@qq.com
 * @date 2019/3/17 11:33
 */
public interface QueryBuilder {

    String getSortDescribe();

    String getPageDescribe();

    Pageable getPageable();

    void setPageable(Pageable pageable);

    Sort getSortable();

    CompareBuilder setSortable(Sort sortable);

    CompareBuilder setSortable(Sort sortable, Map<String, String> fields);

    String getGroupBy();

    String getHaving();

    CompareBuilder groupBy(String groupBy);

    CompareBuilder having(String having);

    CompareBuilder startGroup();

    CompareBuilder startGroup(Boolean and);

    CompareBuilder endGroup();

    CompareBuilder filter(String field, Object value);

    CompareBuilder filter(String field, Object value, Boolean and);

    CompareBuilder filter(String field, QueryOperator operator, Object value);

    CompareBuilder filter(String field, QueryOperator operator, Object value, Boolean and);

    CompareBuilder compare(String field, Object value);

    CompareBuilder compare(String field, Object value, Boolean and);

    CompareBuilder compare(String field, QueryOperator operator, Object value);

    CompareBuilder compare(String field, QueryOperator operator, Object value, Boolean and);

    CompareBuilder in(String field, List value);

    CompareBuilder in(String field, List value, Boolean and);

    CompareBuilder in(String field, List value, Boolean and, Boolean allowEmpey);

    CompareBuilder notIn(String field, List value);

    CompareBuilder notIn(String field, List value, Boolean and);

    CompareBuilder notIn(String field, List value, Boolean and, Boolean allowEmpey);

    CompareBuilder like(String field, Object value);

    CompareBuilder like(String field, Object value, Boolean and);

    CompareBuilder like(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder rLike(String field, Object value);

    CompareBuilder rLike(String field, Object value, Boolean and);

    CompareBuilder rLike(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder lLike(String field, Object value);

    CompareBuilder lLike(String field, Object value, Boolean and);

    CompareBuilder lLike(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder notLike(String field, Object value);

    CompareBuilder notLike(String field, Object value, Boolean and);

    CompareBuilder notLike(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder notRLike(String field, Object value);

    CompareBuilder notRLike(String field, Object value, Boolean and);

    CompareBuilder notRLike(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder notLLike(String field, Object value);

    CompareBuilder notLLike(String field, Object value, Boolean and);

    CompareBuilder notLLike(String field, Object value, Boolean and, Boolean allowEmpey);

    CompareBuilder between(String field, Object start, Object end);

    CompareBuilder between(String field, Object start, Object end, Boolean and);

    CompareBuilder notBetween(String field, Object start, Object end);

    CompareBuilder notBetween(String field, Object start, Object end, Boolean and);

    CompareBuilder where(String field, Object value, QueryOperator operator, Boolean and, Boolean allowEmpey);

    CompareBuilder addInCondition(String field, Collection value, Boolean in, Boolean and, Boolean allowEmpey);

    CompareBuilder addSearchCondition(String field, Object value, Boolean and, Boolean like, Boolean left, Boolean right, Boolean allowEmpey);

    CompareBuilder addCondition(String field);

    CompareBuilder addCondition(String field, Boolean and);

    CompareBuilder addCondition(String field, Map<String, Object> param);

    CompareBuilder addCondition(String field, Map<String, Object> param, Boolean and);

    CompareBuilder addStatementCondition(String field, Boolean and);

    CompareBuilder addFieldCondition(String field, QueryOperator operator, Collection value, Boolean and);

    CompareBuilder addFieldCondition(String field, QueryOperator operator, Map value, Boolean and);

    CompareBuilder addFieldCondition(String field, QueryOperator operator, String value, Boolean and);

    CompareBuilder.CompareField getCompareField(String field, QueryOperator operator, Boolean and, QueryFieldValueType type, Boolean isStatement);

}
