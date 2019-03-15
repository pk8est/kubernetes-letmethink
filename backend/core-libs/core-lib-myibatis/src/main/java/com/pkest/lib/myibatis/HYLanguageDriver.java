package com.pkest.lib.myibatis;

import com.pkest.lib.myibatis.annotation.HYColumn;
import com.pkest.lib.myibatis.annotation.HYTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created by wuzhonggui on 2018/11/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern updateSetPattern = Pattern.compile("@\\{updateSet\\|?(\\w+)?\\}");
    public static String tablePlaceholder = "@{table}";
    public static String idPlaceholder = "@{id}";
    public static String idFieldPlaceholder = "@{idField}";
    public static String idValuePlaceholder = "@{idValue}";
    public static String fieldsPlaceholder = "@{field}";
    public static String insertFieldPlaceholder = "@{insertField}";
    public static String insertValuePlaceholder = "@{insertValue}";
    public static String batchInsertFieldPlaceholder = "@{batchInsertField}";
    public static String batchInsertValuePlaceholder = "@{batchInsertValue}";
    public static String updateSetPlaceholder = "@{updateSet}";
    public static String conditionPlaceholder = "@{where}";

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql){
        return super.createParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Class<?> mapperClass = HYMybatisMapperRegistry.getCurrentMapper();

        Class<?>[] generics = HYMybatisReflectUtil.getMapperGenerics(mapperClass);
        if(generics.length < 2){
            return super.createSqlSource(configuration, script, parameterType);
        }
        Class<?> modelClass = generics[0];
        Class<?> idClass = generics[1];

        ResultMap resultMap = getResultMap(configuration.getResultMaps(), modelClass);
        script = setTable(script, mapperClass, modelClass, idClass, resultMap);
        script = setId(script, mapperClass, modelClass, idClass, resultMap);
        script = setIdFeild(script, mapperClass, modelClass, idClass, resultMap);
        script = setIdValue(script, mapperClass, modelClass, idClass, resultMap);
        script = setField(script, mapperClass, modelClass, idClass, resultMap);
        script = setInsertField(script, mapperClass, modelClass, idClass, resultMap);
        script = setInsertValue(script, mapperClass, modelClass, idClass, resultMap);
        script = setBatchInsertField(script, mapperClass, modelClass, idClass, resultMap);
        script = setBatchInsertValue(script, mapperClass, modelClass, idClass, resultMap);
        script = setUpdateSet(script, mapperClass, modelClass, idClass, resultMap);
        script = setCondition(script, mapperClass, modelClass, idClass, resultMap);

        HYMybatisMapperRegistry.setCurrentMapperId(getIdField(modelClass));
        return super.createSqlSource(configuration, script, parameterType);
    }

    private ResultMap getResultMap(Collection<ResultMap> resultMaps, Class<?> modelClass) {
        /*for (ResultMap resultMap : resultMaps)
            if (modelClass == resultMap.getType() && !resultMap.getId().contains("-"))
                return resultMap;*/
        return null;
    }

    /**
     * 设置表名
     * 把 ResultMap 的 id 值作为表名，
     * 若 ResultMap 不存在，则把驼峰命名法的 Model 类名转以下划线命名作为表名
     *
     * @param script
     * @param modelClass
     * @param resultMap
     * @return
     */
    private String setTable(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(tablePlaceholder)) {
            String tableName;
            HYTable annotation = modelClass.getAnnotation(HYTable.class);

            if (resultMap != null) {
                tableName = resultMap.getId().substring(mapperClass.getName().length() + 1);
            }else if(annotation != null && StringUtils.isNotBlank(annotation.value())) {
                tableName = annotation.value();
            }else {
                tableName = toUnderline(modelClass.getSimpleName());
            }
            script = script.replace(tablePlaceholder, tableName);
        }
        return script;
    }

    private String setId(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(idPlaceholder)) {
            script = script.replace(idPlaceholder, "`" + getId(modelClass, resultMap) + "`=" + "#{" + getIdProperty(modelClass) + "}");
        }
        return script;
    }

    private String setIdFeild(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(idFieldPlaceholder)) {
            script = script.replace(idFieldPlaceholder, "`" + getId(modelClass, resultMap) + "`");
        }
        return script;
    }

    private String setIdValue(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(idValuePlaceholder)) {
            script = script.replace(idValuePlaceholder, "#{" + getIdProperty(modelClass) + "}");
        }
        return script;
    }

    private String getId(Class<?> modelClass, ResultMap resultMap){
        String idName;
        Field idField = getIdField(modelClass);
        ResultMapping resultMapping = getIdResultMapping(resultMap, null);
        if (resultMapping != null) {
            idName = resultMapping.getColumn();
        }else if(idField != null){
            idName = toUnderline(idField.getName());
        }else {
            idName = "id";
        }
        return idName;
    }

    private String getIdProperty(Class<?> modelClass){
        Field idField = getIdField(modelClass);
        return idField == null ? "id" : idField.getName();
    }

    private Field getIdField(Class<?> modelClass){
        HYColumn column;
        Field[] fields = modelClass.getDeclaredFields();
        for(Field field: fields){
            column = field.getAnnotation(HYColumn.class);
            if(column != null && column.pk()){
                return field;
            }
        }
        return null;
    }

    private String setField(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(fieldsPlaceholder)) {
            StringBuilder fieldsBuilder = new StringBuilder();
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                if(Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false)){
                    fieldsBuilder.append("`").append(getColumnName(field)).append("`,");
                }
            }
            String fieldsName = fieldsBuilder.toString();
            if(fieldsName.endsWith(",")) fieldsName = fieldsName.substring(0, fieldsName.length() - 1);
            if(StringUtils.isBlank(fieldsName)) fieldsName = "*";
            script = script.replace(fieldsPlaceholder, fieldsName);
        }
        return script;
    }

    private String setInsertField(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(insertFieldPlaceholder)) {
            StringBuilder fieldsBuilder = new StringBuilder("<trim prefixOverrides=\",\">");
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                if(Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false && column.insertable())){
                    fieldsBuilder.append("<if test=\""+getPropertyName(field)+"!=null\">,`"+getColumnName(field)+"`</if>");
                }
            }
            fieldsBuilder.append("</trim>");
            script = script.replace(insertFieldPlaceholder, fieldsBuilder.toString());
        }
        return script;
    }

    private String setInsertValue(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(insertValuePlaceholder)) {
            String fieldName;
            StringBuilder fieldsBuilder = new StringBuilder("<trim prefixOverrides=\",\">");
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                if(Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false && column.insertable())){
                    fieldName = getPropertyName(field);
                    fieldsBuilder.append("<if test=\""+fieldName+"!=null\">,#{"+fieldName+"}</if>");
                }
            }
            fieldsBuilder.append("</trim>");
            script = script.replace(insertValuePlaceholder, fieldsBuilder.toString());
        }
        return script;
    }

    private String setBatchInsertField(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(batchInsertFieldPlaceholder)) {
            StringBuilder builder = new StringBuilder("<trim prefixOverrides=\",\">");
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false && column.insertable())){
                    builder.append(",`"+getColumnName(field)+"`");
                }
            }
            builder.append("</trim>");
            script = script.replace(batchInsertFieldPlaceholder, builder.toString());
        }
        return script;
    }

    private String setBatchInsertValue(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(batchInsertValuePlaceholder)) {
            String fieldName;
            StringBuilder builder = new StringBuilder();
            builder.append("<foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\">(");
            builder.append("<trim prefixOverrides=\",\">");
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false && column.insertable())){
                    fieldName = getPropertyName(field);
                    builder.append(",#{item."+fieldName+"}");
                }
            }
            builder.append("</trim>");
            builder.append(")</foreach>");
            script = script.replace(batchInsertValuePlaceholder, builder.toString());
        }
        return script;
    }

    private String setUpdateSet(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(updateSetPlaceholder)) {
            StringBuilder fieldsBuilder = new StringBuilder("<trim prefixOverrides=\",\">");
            Field[] fields = modelClass.getDeclaredFields();
            for(Field field: fields){
                if(Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                if(field.getName().equals(getIdProperty(modelClass))) {
                    continue;
                }
                HYColumn column = field.getAnnotation(HYColumn.class);
                if(column == null || (column.invisible() == false && column.updatable() && column.pk() == false)){
                    fieldsBuilder.append("<if test=\""+getPropertyName(field)+"!=null\">,`"+getColumnName(field)+"`=#{"+getPropertyName(field)+"}</if>");
                }
            }
            fieldsBuilder.append("</trim>");
            script = script.replace(updateSetPlaceholder, fieldsBuilder.toString());
        }
        return script;
    }

    private String setCondition(String script, Class<?> mapperClass, Class<?> modelClass, Class<?> idClass, ResultMap resultMap) {
        if (script.contains(conditionPlaceholder)) {
            script = script.replace(conditionPlaceholder,
                    "<foreach collection=\"where\" index=\"__index\" item=\"__item\">" +
                            "<if test=\"__index.isStatement()\">" +
                            "\\${__index.getConnector()} \\${__index.getField()}" +
                            "</if>" +
                            "<if test=\"__index.isList() and (__index.getOperator().getValue() == 'IN' or __index.getOperator().getValue() == 'NOT IN')\">" +
                            "\\${__index.getConnector()} \\${__index.getField()} \\${__index.getOperator().getValue()}" +
                            "(" +
                            "<foreach collection=\"__item\" item=\"__item2\" separator=\",\">" +
                            "#{__item2}" +
                            "</foreach>" +
                            ")" +
                            "</if>" +
                            "<if test=\"__index.isList() and (__index.getOperator().getValue() == 'BETWEEN' or __index.getOperator().getValue() == 'NOT BETWEEN')\">" +
                            "\\${__index.getConnector()} \\${__index.getField()} \\${__index.getOperator().getValue()}" +
                            "<foreach collection=\"__item\" item=\"__item2\" separator=\"AND\">" +
                            "#{__item2}" +
                            "</foreach>" +
                            "</if>" +
                            "<if test=\"!__index.isStatement() and __index.isSimple()\">" +
                            "\\${__index.getConnector()} \\${__index.getField()} \\${__index.getOperator().getValue()} #{__item}" +
                            "</if>" +
                            "<if test=\"!__index.isStatement() and __index.isMap()\">" +
                            "\\${__index.getConnector()} \\${__index.getField()} \\${__index.getOperator().getValue()}" +
                            "</if>" +
                            "</foreach>" +
                            " \\${where.getGroupBy()}" +
                            " \\${where.getHaving()}" +

                           /* "<if test=\"where.getSortable()\">" +
                            "<foreach collection=\"where.getSortable()\" item=\"sort\" open=\" ORDER BY \" separator=\",\">" +
                            "\\${sort.getProperty()} \\${sort.getDirection()}" +
                            "</foreach>" +
                            "</if>" +*/

                            " \\${where.getSortDescribe()}" +
                            " \\${where.getPageDescribe()}"
            );
        }
        return script;
    }

    private boolean isIdField(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            for (ResultMapping resultMapping : resultMap.getIdResultMappings()) {
                if (resultMapping.getProperty().equals(field.getName()))
                    return true;
            }
        }
        return false;
    }

    private ResultMapping getIdResultMapping(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            if (resultMap.getIdResultMappings().size() > 0)
                return resultMap.getIdResultMappings().get(0);
        }
        return null;
    }

    private ResultMapping getResultMapping(ResultMap resultMap, Field field) {
        if (resultMap != null) {
            for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                if (resultMapping.getProperty().equals(field.getName()))
                    return resultMapping;
            }
        }
        return null;
    }

    private String getEmptyTesting(Field field) {
        if (String.class == field.getType()) {
            return String.format("%s != null and %s != ''", field.getName(), field.getName());
        } else {
            return String.format("%s != null", field.getName());
        }
    }

    private String getColumnName(Field field) {
        HYColumn column = field.getAnnotation(HYColumn.class);
        if(column == null || StringUtils.isBlank(column.value())){
            return toUnderline(field.getName());
        }else{
            return column.value();
        }
    }

    private String getPropertyName(Field field) {
        return field.getName();
    }


    private String toUnderline(String str) {
        StringBuilder buf = new StringBuilder();
        buf.append(Character.toLowerCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                buf.append("_" + Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

}
