package com.handmark.pulltorefresh.library;

import java.util.HashMap;
import java.util.Set;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.handmark.pulltorefresh.library.internal.LoadingLayout;

public class LoadingLayoutProxy implements ILoadingLayout {

	private final HashMap<String, LoadingLayout> mLoadingLayouts;

	LoadingLayoutProxy() {
		mLoadingLayouts = new HashMap<String, LoadingLayout>();
	}

	/**
	 * This allows you to add extra LoadingLayout instances to this proxy. This
	 * is only necessary if you keep your own instances, and want to have them
	 * included in any
	 * {@link PullToRefreshBase#createLoadingLayoutProxy(boolean, boolean)
	 * createLoadingLayoutProxy(...)} calls.
	 * 
	 * @param layout - LoadingLayout to have included.
	 */
	public void addLayout(LoadingLayout layout) {
		if (null != layout) {
            if (!mLoadingLayouts.containsValue(layout)) {
                mLoadingLayouts.put(String.valueOf(layout.hashCode()), layout);
            }
		}
	}

    public void addLayout(String key, LoadingLayout layout) {
        mLoadingLayouts.put(key, layout);
    }

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setLastUpdatedLabel(label);
        }
	}

	@Override
	public void setLoadingDrawable(Drawable drawable) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setLoadingDrawable(drawable);
        }
	}

    public final void setBackgroundColor(int color) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setBackgroundColor(color);
        }
    }

	@Override
	public void setRefreshingLabel(CharSequence refreshingLabel) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setRefreshingLabel(refreshingLabel);
        }
	}

	@Override
	public void setPullLabel(CharSequence label) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setPullLabel(label);
        }
	}

	@Override
	public void setReleaseLabel(CharSequence label) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setReleaseLabel(label);
        }
	}

	public void setTextTypeface(Typeface tf) {
        Set<String> keySet = mLoadingLayouts.keySet();
        for (String key : keySet) {
            LoadingLayout layout = mLoadingLayouts.get(key);
            layout.setTextTypeface(tf);
        }
	}

    private void setLastUpdatedLabel(String key, CharSequence label) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setLastUpdatedLabel(label);
    }

    public void setFooterLastUpdatedLabel(CharSequence label) {
        setLastUpdatedLabel("footer", label);
    }

    public void setHeaderLastUpdatedLabel(CharSequence label) {
        setLastUpdatedLabel("header", label);
    }

    private void setLoadingDrawable(String key, Drawable drawable) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setLoadingDrawable(drawable);
    }

    public void setHeaderLoadingDrawable(Drawable drawable) {
        setLoadingDrawable("header", drawable);
    }

    public void setFooterLoadingDrawable(Drawable drawable) {
        setLoadingDrawable("footer", drawable);
    }

    private final void setBackgroundColor(String key, int color) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setBackgroundColor(color);
    }

    public final void setHeaderBackgroundColor(int color) {
        setBackgroundColor("header", color);
    }

    public final void setFooterBackgroundColor(int color) {
        setBackgroundColor("footer", color);
    }

    private void setRefreshingLabel(String key, CharSequence refreshingLabel) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setRefreshingLabel(refreshingLabel);
    }

    public void setHeaderRefreshingLabel(CharSequence refreshingLabel) {
        setRefreshingLabel("header", refreshingLabel);
    }

    public void setFooterRefreshingLabel(CharSequence refreshingLabel) {
        setRefreshingLabel("footer", refreshingLabel);
    }

    private void setPullLabel(String key, CharSequence label) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setPullLabel(label);
    }

    public void setHeaderPullLabel(CharSequence label) {
        setPullLabel("header", label);
    }

    public void setFooterPullLabel(CharSequence label) {
        setPullLabel("footer", label);
    }

    private void setReleaseLabel(String key, CharSequence label) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setReleaseLabel(label);
    }

    public void setHeaderReleaseLabel(CharSequence label) {
        setReleaseLabel("header", label);
    }

    public void setFooterReleaseLabel(CharSequence label) {
        setReleaseLabel("footer", label);
    }

    private void setTextTypeface(String key, Typeface tf) {
        LoadingLayout layout = mLoadingLayouts.get(key);
        layout.setTextTypeface(tf);
    }

    public void setHeaderTextTypeface(Typeface tf) {
        setTextTypeface("header", tf);
    }

    public void setFooterTextTypeface(Typeface tf) {
        setTextTypeface("footer", tf);
    }
}
