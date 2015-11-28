package com.example.money;

import android.app.Fragment;
import android.os.Bundle;
import com.example.money.entity.PageInfo;
import com.example.money.common.PageReferenceManager;

/**
 * Created by su on 2014/8/22.
 */
public abstract class CommonFragment extends Fragment {

    protected String mPageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageId = Integer.toHexString(hashCode());
        PageReferenceManager.addPage(mPageId, new PageInfo(getClass().getSimpleName(), mPageId));
        PageReferenceManager.logCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PageReferenceManager.removePage(mPageId);
        PageReferenceManager.logCache();
    }

    protected abstract String getLogTag();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
