package com.example.money.entity;

/**
 * Created by su on 15-9-17.
 */
public class PageInfo {
    public String pageName;
    public String pageId;
    public String tabCode;
    public String subCode;
    public boolean all = false;
    public StateRefresh state = StateRefresh.DONE;
//    public boolean needToRefresh = false;

    public PageInfo(String pageName, String pageId) {
        this.pageName = pageName;
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageName='" + pageName + '\'' +
                ", pageId='" + pageId + '\'' +
                ", tabCode='" + tabCode + '\'' +
                ", subCode='" + subCode + '\'' +
                ", all=" + all +
                ", state=" + state +
                '}';
    }

    public enum StateRefresh {
        NEED_TO_REFRESH,
        REFRESHING,
        DONE
    }
}
