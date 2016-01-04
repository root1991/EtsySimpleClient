package com.test.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.interfaces.Manager;
import com.test.util.CachedValue;

import java.util.HashSet;
import java.util.Set;


public class SharedPrefManager implements Manager {

    private static final String NAME = "sharedPrefs";

    private SharedPreferences sp;

    private Set<CachedValue> cachedValues;


    @Override
    public void init(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        CachedValue.initialize(sp);
        cachedValues = new HashSet<>();
    }

    @Override
    public void clear() {
        for (CachedValue value : cachedValues) {
            value.delete();
        }
    }

}
