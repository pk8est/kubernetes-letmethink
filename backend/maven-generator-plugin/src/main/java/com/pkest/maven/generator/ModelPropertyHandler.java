package com.pkest.maven.generator;

import com.pkest.maven.generator.bean.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhonggui on 2019/4/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class ModelPropertyHandler {

    private SqlHelper sqlHelper = null;

    public ModelPropertyHandler(String url, String username, String password) {
        sqlHelper = new SqlHelper(url, username, password);
    }

    public List<Property> getProperties(String tablename) {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM " + tablename + " limit 1;";
        Connection con = null;
        PreparedStatement pStemt = null;
        try {
            con = sqlHelper.getConnection();
            pStemt = con.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount(); // 统计列
            for (int i = 0; i < size; i++) {
                Property property = new Property();
                property.setColumn(rsmd.getColumnName(i + 1).replace(" ", ""));
                property.setName(Utils.toCamel(property.getColumn()));
                property.setType(sqlType2JavaType(rsmd.getColumnTypeName(i + 1)));
                property.setLength(rsmd.getColumnDisplaySize(i + 1));
                properties.add(property);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pStemt != null) {
                    pStemt.close();
                }
                if(sqlHelper != null){
                    sqlHelper.closeConnection(con);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint") || sqlType.equalsIgnoreCase("tinyINT UNSIGNED")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("INT UNSIGNED")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")||sqlType.equalsIgnoreCase("DOUBLE") ) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }else if (sqlType.equalsIgnoreCase("TIMESTAMP")){
            return "Date";
        }

        return null;
    }


}
