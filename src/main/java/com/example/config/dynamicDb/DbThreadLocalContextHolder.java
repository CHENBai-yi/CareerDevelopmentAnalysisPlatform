package com.example.config.dynamicDb;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.dynamicDb
 * Date:2023/11/27 13:47
 *
 * @author BaiYiChen
 */
public class DbThreadLocalContextHolder {
    private static final InheritableThreadLocal<String> THREAD_LOCAL_DB = new InheritableThreadLocal<>();

    public static String getDbUse() {
        return THREAD_LOCAL_DB.get();
    }

    public static void setDbUse(final String dbName) {
        THREAD_LOCAL_DB.set(dbName);
    }

    public static void poll() {
        THREAD_LOCAL_DB.remove();
    }

}
