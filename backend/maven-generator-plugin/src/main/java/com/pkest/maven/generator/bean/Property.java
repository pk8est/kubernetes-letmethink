package com.pkest.maven.generator.bean;

/**
 * Created by wuzhonggui on 2019/4/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class Property {
    private String name;
    private String column;
    private String type;
    private int length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
