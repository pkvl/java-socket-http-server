package com.anroypaul.javasockethttpserver.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlQueries {

    public static String selectFromTable(String tableName) {
        return "SELECT * FROM " + tableName + ";";
    }

    public static String getEntityById(String tableName, int id) {
        StringBuilder sb = new StringBuilder(selectFromTable(tableName));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE id = ").append(id).append(";");
        return sb.toString();
    }

    public static String insertIntoTableValues(String tableName, String... values) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" VALUES");
        sb.append("(");
        for(int i = 0; i < values.length; i++) {
            sb.append("'").append(values[i]).append("'");
            if(i < values.length - 1) {
                sb.append(",");
            }
        }
        sb.append(");");
        return sb.toString();
    }
}
