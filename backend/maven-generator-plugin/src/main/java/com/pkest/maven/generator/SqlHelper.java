package com.pkest.maven.generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhonggui on 2019/4/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class SqlHelper {
    private String url;
    private String username;
    private String password;

    private Connection connection;

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public SqlHelper(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> get(String sql, String columnName) {
        List<T> result = new ArrayList<T>();
        Statement statement = null;
        try {
            statement = getStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                result.add((T) set.getObject(columnName));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeStatement(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Statement getStatement() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(url, username, password);
        Statement statement = con.createStatement();
        return statement;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;
    }

    public void closeConnection(Connection conn) throws ClassNotFoundException, SQLException {
        if (connection != null) {
            connection.close();
        }

        if (conn != null) {
            conn.close();
        }

    }

    public void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            Connection con = statement.getConnection();
            statement.close();
            if (con != null) {
                con.close();
            }
        }
    }
}
