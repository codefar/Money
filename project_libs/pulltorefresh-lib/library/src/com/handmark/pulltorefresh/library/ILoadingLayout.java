package com.handmark.pulltorefresh.library;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface ILoadingLayout {

	/**
	 * Set the Last Updated Text. This displayed under the main label when
	 * Pulling
	 * 
	 * @param label - Label to set
	 */
    public void setLastUpdatedLabel(CharSequence label);
    public void setHeaderLastUpdatedLabel(CharSequence label);
    public void setFooterLastUpdatedLabel(CharSequence label);

	/**
	 * Set the drawable used in the loading layout. This is the same as calling
	 * <code>setLoadingDrawable(drawable, Mode.BOTH)</code>
	 * 
	 * @param drawable - Drawable to display
	 */
    public void setLoadingDrawable(Drawable drawable);
    public void setHeaderLoadingDrawable(Drawable drawable);
    public void setFooterLoadingDrawable(Drawable drawable);

    public void setBackgroundColor(int color);
    public void setHeaderBackgroundColor(int color);
    public void setFooterBackgroundColor(int color);

	/**
	 * Set Text to show when the Widget is being Pulled
	 * <code>setPullLabel(releaseLabel, Mode.BOTH)</code>
	 * 
	 * @param pullLabel - CharSequence to display
	 */
    public void setPullLabel(CharSequence pullLabel);
    public void setHeaderPullLabel(CharSequence pullLabel);
    public void setFooterPullLabel(CharSequence pullLabel);

	/**
	 * Set Text to show when the Widget is refreshing
	 * <code>setRefreshingLabel(releaseLabel, Mode.BOTH)</code>
	 * 
	 * @param refreshingLabel - CharSequence to display
	 */
    public void setRefreshingLabel(CharSequence refreshingLabel);
    public void setHeaderRefreshingLabel(CharSequence refreshingLabel);
    public void setFooterRefreshingLabel(CharSequence refreshingLabel);

	/**
	 * Set Text to show when the Widget is being pulled, and will refresh when
	 * released. This is the same as calling
	 * <code>setReleaseLabel(releaseLabel, Mode.BOTH)</code>
	 * 
	 * @param releaseLabel - CharSequence to display
	 */
    public void setReleaseLabel(CharSequence releaseLabel);
    public void setHeaderReleaseLabel(CharSequence releaseLabel);
    public void setFooterReleaseLabel(CharSequence releaseLabel);

	/**
	 * Set's the Sets the typeface and style in which the text should be
	 * displayed. Please see
	 * {@link android.widget.TextView#setTypeface(Typeface)
	 * TextView#setTypeface(Typeface)}.
	 */
    public void setTextTypeface(Typeface tf);
    public void setHeaderTextTypeface(Typeface tf);
    public void setFooterTextTypeface(Typeface tf);
}
