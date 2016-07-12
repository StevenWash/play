package com.test.api.util;


import java.util.Collections;
import java.util.List;

/**
 * Created by zengming on 11/26/15.
 */
public class ListUtil {

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.<T>emptyList() : list;
    }
}
