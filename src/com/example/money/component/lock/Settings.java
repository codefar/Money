package com.example.money.component.lock;

import android.content.SharedPreferences;

public class Settings {

    public static class Secure {
        /**
         * Whether autolock is enabled (0 = false, 1 = true)
         */
        public static final String LOCK_PATTERN_ENABLED = "lock_pattern_autolock";
        /**
         * Whether lock pattern is visible as user enters (0 = false, 1 = true)
         */
        public static final String LOCK_PATTERN_VISIBLE = "lock_pattern_visible_pattern";

        /**
         * Whether lock pattern will vibrate as user enters (0 = false, 1 = true)
         */
        public static final String LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED =
                "lock_pattern_tactile_feedback_enabled";

        public static int getInt(SharedPreferences sp, String name, int def) {
            return sp.getInt(name, def);
        }

        public static void putInt(SharedPreferences sp, String name, int value) {
            sp.edit().putInt(name, value).apply();
        }

        public static boolean getBoolean(SharedPreferences sp, String name, boolean def) {
            return sp.getBoolean(name, def);
        }

        public static void putBoolean(SharedPreferences sp, String name, boolean value) {
            sp.edit().putBoolean(name, value).apply();
        }

        public static long getLong(SharedPreferences sp, String name, long def) {
            return sp.getLong(name, def);
        }

        public static void putLong(SharedPreferences sp, String name, long value) {
            sp.edit().putLong(name, value).apply();
        }

        public static String getString(SharedPreferences sp, String name) {
            return sp.getString(name, "");
        }

        public static void putString(SharedPreferences sp, String name, String value) {
            sp.edit().putString(name, value).apply();
        }
    }

    public static class System {
        /**
         * A formatted string of the next alarm that is set, or the empty string
         * if there is no alarm set.
         */
        public static final String NEXT_ALARM_FORMATTED = "next_alarm_formatted";

        public static String getString(SharedPreferences sp, String name) {
            return sp.getString(name, "");
        }

        public static void putString(SharedPreferences sp, String name, String value) {
            sp.edit().putString(name, value).apply();
        }
    }
}
