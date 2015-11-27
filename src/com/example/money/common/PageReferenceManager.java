package com.example.money.common;

import android.util.Log;
import android.util.LruCache;
import com.example.money.entity.PageInfo;

import java.util.Map;
import java.util.Set;

/**
 * Created by su on 15-9-18.
 */
public class PageReferenceManager {
    private static final String TAG = PageReferenceManager.class.getSimpleName();
    private static final int MAX_SIZE = 100;
    private static LruCache<String, PageInfo> sPageLruCache = new LruCache<String, PageInfo>(MAX_SIZE);

    public static void addPage(String key, PageInfo ai) {
        sPageLruCache.put(key, ai);
    }

    public static void removePage(String key) {
        sPageLruCache.remove(key);
    }

    public static void setRefreshByKey(String key, PageInfo.StateRefresh state) {
        PageInfo pi = sPageLruCache.get(key);
        if (pi == null) {
            return;
        }

        pi.state = state;
    }

    /**
     * 用于一般Activity和一般Fragment
     */
    public static void setRefreshByClassName(String className, PageInfo.StateRefresh state) {
        Map<String, PageInfo> snapshot = sPageLruCache.snapshot();
        Set<String> set = snapshot.keySet();
        for (String key : set) {
            PageInfo pi = sPageLruCache.get(key);
            if (pi != null && className.equals(pi.pageName)) {
                pi.state = state;
            }
        }
    }

    /**
     * 用于多实例Fragment
     */
    public static void setRefreshByClassNameAndCode(String className, String code, PageInfo.StateRefresh state) {
        Map<String, PageInfo> snapshot = sPageLruCache.snapshot();
        Set<String> set = snapshot.keySet();
        for (String key : set) {
            PageInfo pi = sPageLruCache.get(key);
            if (pi != null
                    && className.equals(pi.pageName)
                    && code.equals(pi.tabCode)) {
                pi.state = state;
            }
        }
    }

    /**
     * 用于多实例Fragment
     */
    public static void setRefreshByClassNameAndAllCode(String className, String code, String subCode, PageInfo.StateRefresh state) {
        Map<String, PageInfo> snapshot = sPageLruCache.snapshot();
        Set<String> set = snapshot.keySet();
        for (String key : set) {
            PageInfo pi = sPageLruCache.get(key);
            if (pi != null
                    && className.equals(pi.pageName)
                    && code.equals(pi.tabCode)
                    && subCode.equals(pi.subCode)) {
                pi.state = state;
            }
        }
    }

    public static PageInfo.StateRefresh needToRefresh(String key) {
        PageInfo ai = sPageLruCache.get(key);
        if (ai == null) {
            return PageInfo.StateRefresh.NEED_TO_REFRESH;
        }

        return ai.state;
    }

    public static PageInfo getPageInfo(String key) {
        return sPageLruCache.get(key);
    }

    public static void logCache() {
        Log.d(TAG, "sPageLruCache: " + sPageLruCache.snapshot());
        Log.d(TAG, "------- logCache done -------");
    }

    public static void dump() {
        Log.d("DUMP_PAGE_LRU_CACHE", "MAX_SIZE: " + MAX_SIZE);
        Map<String, PageInfo> snapshot = sPageLruCache.snapshot();
        Set<String> set = snapshot.keySet();
        for (String key : set) {
            PageInfo pi = sPageLruCache.get(key);
            Log.d("DUMP_PAGE_LRU_CACHE", "element{ " + key + " : " + pi + " }");
        }
        Log.d("DUMP_PAGE_LRU_CACHE", "total: " + set.size());
    }
}
