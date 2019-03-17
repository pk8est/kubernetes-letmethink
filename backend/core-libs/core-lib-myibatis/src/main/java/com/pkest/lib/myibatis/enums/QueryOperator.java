package com.pkest.lib.myibatis.enums;

/**
 * @author 360733598@qq.com
 * @date 2019/3/17 12:22
 */
public enum QueryOperator {
    EQ("="), NEQ("<>"), GT(">"), LT("<"), EGT(">="), ELT("<="), IN("IN"), NOTIN("NOT IN"), BETWEEN("BETWEEN"), NOTBETWEEN("NOT BETWEEN"),
    BITAND("&"), BITOR("|"), BITNOTOR("^"), BITNOT("^"), AND("&&"),OR("||"),NOT("NOT"),REGEXP("REGEXP"),
    LIKE("LIKE"), NOTLIKE("NOT LIKE"), NULL("IS NULL"), NOTNULL("IS NOT NULL"), LMOVE("<<"), RMOVE(">>"), EMPTY("");

    private final String value;
    QueryOperator(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString(){
        return getValue();
    }
}
