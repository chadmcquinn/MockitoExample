package com.inin.mumbai;

public class Repository {
    public static final String VERSION = "Version";
    private Database database;

    public Repository(Database database) {
        this.database = database;
        database.setValueForKey(VERSION, "1.0.0");
    }

    public String getVersion()
    {
        return database.getValueForKey(VERSION);
    }

}
