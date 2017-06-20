package budget.saveit.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aa on 18/06/17.
 */

public class Parameters {
    private static final String SHARED_PREFERENCES_FILE_NAME = "saveit_sp";
    private static Parameters instance;
    private SharedPreferences preferences;

    private Parameters(Context context) {
        if (context == null) {
            throw new NullPointerException("Context is null XD");
        }

        preferences = context.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized Parameters getInstance(Context context) {
        if (instance == null) {
            instance = new Parameters(context);
        }

        return instance;
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }
}
