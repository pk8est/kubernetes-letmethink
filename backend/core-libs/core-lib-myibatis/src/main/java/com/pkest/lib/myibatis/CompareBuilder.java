package com.pkest.lib.myibatis;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.pkest.lib.myibatis.enums.QueryFieldValueType;
import com.pkest.lib.myibatis.enums.QueryOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * Created by wuzhonggui on 2017/7/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CompareBuilder extends LinkedHashMap<CompareBuilder.CompareField, Object> implements Cloneable, QueryBuilder{

    private Boolean groupStart = false;
    private Pageable pageable;
    private Sort sortable;
    private String groupBy;
    private String having;
    private transient CompareField tailKey;

    @Override
    public String getSortDescribe(){
        Sort.Order sort = null;
        List<String> sortList = Lists.newArrayList();
        Iterator<Sort.Order> iterator = getSortable() != null ? getSortable().iterator() : null;
        if(iterator != null){
            while (iterator.hasNext()){
                sort = iterator.next();
                sortList.add(sort.getProperty() + " " + sort.getDirection());
            }
        }
        if(sortList.size() > 0){
            return "ORDER BY " + Joiner.on(",").skipNulls().join(sortList);
        }else{
            return "";
        }
    }

    @Override
    public String getPageDescribe(){
        if(getPageable() != null && getPageable().getPageSize() > 0){
            return "LIMIT " + getPageable().getOffset() + "," + getPageable().getPageSize();
        }else{
            return "";
        }
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public Sort getSortable() {
        if(sortable != null){
            return sortable;
        }else{
            return getPageable() != null ? getPageable().getSort() : null;
        }
    }

    @Override
    public CompareBuilder setSortable(Sort sortable) {
        this.sortable = sortable;
        return this;
    }

    @Override
    public CompareBuilder setSortable(Sort sortable, Map<String, String> fields) {
        if(sortable == null){
            this.sortable = sortable;
        }else{
            List<Sort.Order> orders = Lists.newArrayList();
            Iterator<Sort.Order> iterator = sortable.iterator();
            while (iterator.hasNext()){
                Sort.Order order = iterator.next();
                if(fields.containsKey(order.getProperty())){
                    orders.add(new Sort.Order(order.getDirection(), fields.get(order.getProperty())));
                }
            }
            this.sortable = new Sort(orders);
        }
        return this;
    }

    @Override
    public String getGroupBy() {
        return StringUtils.isNotBlank(groupBy) ? "GROUP BY " + groupBy : "";
    }

    @Override
    public String getHaving() {
        return StringUtils.isNotBlank(having) ? "HAVING " + having : "";
    }

    @Override
    public CompareBuilder groupBy(String groupBy){
        this.groupBy = groupBy;
        return this;
    }

    @Override
    public CompareBuilder having(String having){
        this.having = having;
        return this;
    }

    public static CompareBuilder builder(){
        return new CompareBuilder();
    }

    public CompareBuilder(){
        addCondition("1=1");
    }

    public CompareBuilder(String field, Object value){
        this(field, value, null, null);
    }

    public CompareBuilder(String field, Map<String, Object> param){
        addCondition(field, param);
    }

    public CompareBuilder(String field, Object value, Pageable pageable){
        this(field, value, pageable, null);
    }

    public CompareBuilder(String field, Object value, Sort sort){
        this(field, value, null, sort);
    }

    public CompareBuilder(String field, Object value, Pageable pageable, Sort sort){
        filter(field, value);
        setPageable(pageable);
        setSortable(sort);
    }

    public CompareBuilder(Pageable pageable){
        this(pageable, null);
    }

    public CompareBuilder(Sort sort){
        this();
        setSortable(sort);
    }

    public CompareBuilder(Pageable pageable, Sort sort){
        this();
        setPageable(pageable);
        setSortable(sort);
    }

    public CompareBuilder(Map<String, String> conditions){
        for(Map.Entry<String, String> entry: conditions.entrySet()){
            filter(entry.getKey(), entry.getValue());
        }
    }

    public CompareBuilder(String field, QueryOperator operator, Object value){
        filter(field, operator, value);
    }

    public CompareBuilder(String field, List value){
        in(field, value);
    }

    @Override
    public CompareBuilder startGroup(){
        return startGroup(true);
    }

    @Override
    public CompareBuilder startGroup(Boolean and){
        addStatementCondition("(", and);
        groupStart = false;
        return this;
    }

    @Override
    public CompareBuilder endGroup(){
        if(tailKey != null){
            if(!tailKey.getField().equals("(")){
                addStatementCondition(")", null);
            }else{
                this.remove(tailKey);
            }
        }
        return this;
    }

    @Override
    public CompareBuilder filter(String field, Object value){
        return filter(field, QueryOperator.EQ, value);
    }

    @Override
    public CompareBuilder filter(String field, Object value, Boolean and){
        return filter(field, QueryOperator.EQ, value, and);
    }

    @Override
    public CompareBuilder filter(String field, QueryOperator operator, Object value) {
        return filter(field, operator, value, true);
    }

    @Override
    public CompareBuilder filter(String field, QueryOperator operator, Object value, Boolean and){
        return where(field, value, operator, and, false);
    }

    @Override
    public CompareBuilder compare(String field, Object value){
        return compare(field, QueryOperator.EQ, value);
    }

    @Override
    public CompareBuilder compare(String field, Object value, Boolean and){
        return compare(field, QueryOperator.EQ, value, and);
    }

    @Override
    public CompareBuilder compare(String field, QueryOperator operator, Object value){
        return compare(field, operator, value, true);
    }

    @Override
    public CompareBuilder compare(String field, QueryOperator operator, Object value, Boolean and){
        return where(field, value, operator, and, true);
    }

    @Override
    public CompareBuilder in(String field, List value){
        return in(field, value, true);
    }

    @Override
    public CompareBuilder in(String field, List value, Boolean and){
        return in(field, value, and, true);
    }

    @Override
    public CompareBuilder in(String field, List value, Boolean and, Boolean allowEmpey){
        return addInCondition(field, value, true, and, allowEmpey);
    }

    @Override
    public CompareBuilder notIn(String field, List value){
        return in(field, value, true);
    }

    @Override
    public CompareBuilder notIn(String field, List value, Boolean and){
        return notIn(field, value, and, true);
    }

    @Override
    public CompareBuilder notIn(String field, List value, Boolean and, Boolean allowEmpey){
        return addInCondition(field, value, false, and, allowEmpey);
    }

    @Override
    public CompareBuilder like(String field, Object value){
        return like(field, value, true);
    }

    @Override
    public CompareBuilder like(String field, Object value, Boolean and){
        return like(field, value, and, true);
    }

    @Override
    public CompareBuilder like(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, true, true, true, allowEmpey);
    }

    @Override
    public CompareBuilder rLike(String field, Object value){
        return rLike(field, value, true);
    }

    @Override
    public CompareBuilder rLike(String field, Object value, Boolean and){
        return rLike(field, value, and, true);
    }

    @Override
    public CompareBuilder rLike(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, true, false, true, allowEmpey);
    }

    @Override
    public CompareBuilder lLike(String field, Object value){
        return lLike(field, value, true);
    }

    @Override
    public CompareBuilder lLike(String field, Object value, Boolean and){
        return lLike(field, value, and, true);
    }

    @Override
    public CompareBuilder lLike(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, true, true, false, allowEmpey);
    }

    @Override
    public CompareBuilder notLike(String field, Object value){
        return notLike(field, value, true);
    }

    @Override
    public CompareBuilder notLike(String field, Object value, Boolean and){
        return notLike(field, value, and, true);
    }

    @Override
    public CompareBuilder notLike(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, false, true, true, allowEmpey);
    }

    @Override
    public CompareBuilder notRLike(String field, Object value){
        return notRLike(field, value, true);
    }

    @Override
    public CompareBuilder notRLike(String field, Object value, Boolean and){
        return notRLike(field, value, and, true);
    }

    @Override
    public CompareBuilder notRLike(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, false, false, true, allowEmpey);
    }

    @Override
    public CompareBuilder notLLike(String field, Object value){
        return notLLike(field, value, true);
    }

    @Override
    public CompareBuilder notLLike(String field, Object value, Boolean and){
        return notLLike(field, value, and, true);
    }

    @Override
    public CompareBuilder notLLike(String field, Object value, Boolean and, Boolean allowEmpey){
        return addSearchCondition(field, value, and, false, true, false, allowEmpey);
    }

    @Override
    public CompareBuilder between(String field, Object start, Object end){
        return between(field, start, end, true);
    }

    @Override
    public CompareBuilder between(String field, Object start, Object end, Boolean and){
        return addFieldCondition(field, QueryOperator.BETWEEN, Lists.newArrayList(start, end), and);
    }

    @Override
    public CompareBuilder notBetween(String field, Object start, Object end){
        return notBetween(field, start, end, true);
    }

    @Override
    public CompareBuilder notBetween(String field, Object start, Object end, Boolean and){
        return addFieldCondition(field, QueryOperator.NOTBETWEEN, Lists.newArrayList(start, end), and);
    }

    @Override
    public CompareBuilder where(String field, Object value, QueryOperator operator, Boolean and, Boolean allowEmpey){
        if(value instanceof Collection){
            return addInCondition(field, (Collection) value, operator.equals(QueryOperator.NOTIN) ? false : true, and, allowEmpey);
        }else if (value instanceof Arrays){
            return addInCondition(field, Arrays.asList((List)value), operator.equals(QueryOperator.NOTIN) ? false : true, and, allowEmpey);
        }
        String val = value == null ? "" : value.toString();
        if(allowEmpey == true && StringUtils.isEmpty(val)){
            return this;
        }
        return addFieldCondition(field, operator == null ? QueryOperator.EQ : operator, val, and);
    }

    @Override
    public CompareBuilder addInCondition(String field, Collection value, Boolean in, Boolean and, Boolean allowEmpey) {
        value = value == null ? Lists.newArrayList() : value;
        int len = value.size();
        if(allowEmpey == true && len == 0){
            return this;
        }else if(len == 0){
            return addFieldCondition("0", QueryOperator.EQ, "1", and);
        }else if(len == 1 && value instanceof List){
            List newValue = (List) value;
            if(newValue.get(0) == null){
                return addFieldCondition(field, in ? QueryOperator.NULL : QueryOperator.NOTNULL, "", and);
            }else{
                return addFieldCondition(field, in ? QueryOperator.EQ : QueryOperator.NEQ, newValue.get(0).toString(), and);
            }
        }else{
            addFieldCondition(field, in ? QueryOperator.IN : QueryOperator.NOTIN, value, and);
        }
        return this;
    }

    @Override
    public CompareBuilder addSearchCondition(String field, Object value, Boolean and, Boolean like, Boolean left, Boolean right, Boolean allowEmpey) {
        String val = value == null ? "" : value.toString();
        if(allowEmpey == true && StringUtils.isEmpty(val)){
            return this;
        }
        val = escape(val);
        if(left){
            val = "%" + val;
        }
        if(right){
            val = val + "%";
        }
        return addFieldCondition(field, like ? QueryOperator.LIKE : QueryOperator.NOTLIKE, val, and);
    }

    @Override
    public CompareBuilder addCondition(String field){
        return addStatementCondition(field, true);
    }

    @Override
    public CompareBuilder addCondition(String field, Boolean and){
        return addStatementCondition(field, and);
    }

    @Override
    public CompareBuilder addCondition(String field, Map<String, Object> param){
        return addCondition(field, param, true);
    }

    @Override
    public CompareBuilder addCondition(String field, Map<String, Object> param, Boolean and){
        if(param != null){
            for(Map.Entry<String, Object> entry: param.entrySet()){
                field = field.replace("#{"+entry.getKey()+"}", "#{__item."+entry.getKey()+"}");
            }
        }
        return addFieldCondition(field, QueryOperator.EMPTY, param, and);
    }

    @Override
    public CompareBuilder addStatementCondition(String field, Boolean and) {
        put(getCompareField(field, QueryOperator.EMPTY, and, QueryFieldValueType.SIMPLE, true), null);
        return this;
    }

    @Override
    public CompareBuilder addFieldCondition(String field, QueryOperator operator, Collection value, Boolean and) {
        put(getCompareField(field, operator, and, QueryFieldValueType.LIST, false), value);
        return this;
    }

    @Override
    public CompareBuilder addFieldCondition(String field, QueryOperator operator, Map value, Boolean and) {
        put(getCompareField(field, operator, and, QueryFieldValueType.MAP, false), value);
        return this;
    }

    @Override
    public CompareBuilder addFieldCondition(String field, QueryOperator operator, String value, Boolean and) {
        put(getCompareField(field, operator, and, QueryFieldValueType.SIMPLE, false), value);
        return this;
    }

    @Override
    public CompareField getCompareField(String field, QueryOperator operator, Boolean and, QueryFieldValueType type, Boolean isStatement){
        String connector = "";
        if(groupStart == false) {
            groupStart = true;
        }else{
            if(and == null){
                connector = "";
            }else if(and == true){
                connector = "AND";
            }else{
                connector = "OR";
            }
        }
        return new CompareBuilder.CompareField(field, operator, connector, type, isStatement);
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("_", "\\_").replace("%", "\\%");
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<CompareField, Object> entry: this.entrySet()){
            CompareBuilder.CompareField field = entry.getKey();
            sb.append(field.getConnector() != null ? field.getConnector() : "").append(" ").
                    append(field.getField() != null ? field.getField() : "").append(" ").
                    append(field.getOperator() != null ? field.getOperator().getValue() : "").append(" ").
                    append(entry.getValue() != null ? entry.getValue() : "").append(" ");
        }
        sb.append(getGroupBy());
        sb.append(getHaving());
        sb.append(getSortDescribe());
        sb.append(getPageDescribe());
        return sb.toString();
    }

    public class CompareField{
        private String field;
        private QueryOperator operator;
        private String connector;
        private QueryFieldValueType type;

        private Boolean isStatement = false;

        public CompareField(String field, QueryOperator operator, String connector, QueryFieldValueType type, Boolean isStatement){
            this.field = field;
            this.operator = operator == null ? QueryOperator.EMPTY : operator;
            this.connector = connector;
            this.type = type;
            this.isStatement = isStatement;
        }

        public String getField() {
            return field;
        }

        public QueryOperator getOperator() {
            return operator;
        }

        public String getConnector() {
            return connector;
        }

        public Boolean isSimple() {
            return QueryFieldValueType.SIMPLE.equals(type);
        }
        public Boolean isList() {
            return QueryFieldValueType.LIST.equals(type);
        }

        public Boolean isMap() {
            return QueryFieldValueType.MAP.equals(type);
        }

        public Boolean isStatement() {
            return isStatement;
        }
    }
}


