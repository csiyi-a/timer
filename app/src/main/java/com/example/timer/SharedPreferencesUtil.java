package com.example.timer;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static final String mTAG = "timer";



    // 创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;


    // 构造方法
    public SharedPreferencesUtil(Context context) {
        mPreferences = context.getSharedPreferences(mTAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    // 单例模式
    public static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    public  int getSize(){
        return mPreferences.getAll().size();
    }

    public  void clearAll(){
//        mPreferences.getAll().clear();
        mEditor.clear().commit();
    }



    // 存入数据
    public  void putSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    // 存入数据
    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    // 存入数据
    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    // 存入数据
    public void putBoolean(String key, boolean value) {

        mEditor.putBoolean(key, value);

        mEditor.commit();

    }

    // 获取数据
    public String getString(String key) {

        return mPreferences.getString(key, "");
    }

    // 获取数据
    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }
    // 获取数据
    public long getLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    // 获取数据
    public int getInt(String key,int defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }

    // 获取数据
    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }
    // 获取数据
    public boolean getBoolean(String key,boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    // 移除数据
    public void removeSP(String key) {
        try {
            mEditor.remove(key);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
