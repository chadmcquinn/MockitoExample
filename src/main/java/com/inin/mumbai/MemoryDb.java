package com.inin.mumbai;

import java.util.HashMap;
import java.util.Map;

public class MemoryDb implements Database {

    private Map<String, String> store = new HashMap<>();

    public String getValueForKey(String key) {
        return store.get(key);
    }

    public void setValueForKey(String key, String value) {
        store.put(key, value);
    }
}
