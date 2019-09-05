package com.valerityoss.contactsfriendsbook.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by denis on 27.04.2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}

