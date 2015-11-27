package com.example.money.utils;

import java.util.Collection;

public class TypeUtil {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEqualZero(Integer integer) {
        return integer != null && integer == 0;
    }
}
