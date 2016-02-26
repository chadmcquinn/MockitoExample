package com.inin.mumbai;

public interface Database {
    String getValueForKey(String key);
    void setValueForKey(String key, String value);
}
