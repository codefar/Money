package com.example.money;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";

	private FragmentTabHost mTabHost;
	public static final int TAB_MAIN = 0;
	public static final int TAB_FIND = 1;
	public static final int TAB_MY = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initTabs() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getFragmentManager(), R.id.main_layout);

		View tab_main = getLayoutInflater().inflate(R.layout.view_main_tab_main, null);
		View tab_find = getLayoutInflater().inflate(R.layout.view_main_tab_find, null);
		View tab_my = getLayoutInflater().inflate(R.layout.view_main_tab_my, null);

		mTabHost.addTab(mTabHost.newTabSpec("tab_main").setIndicator(tab_main), MainFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab_find").setIndicator(tab_find), FindnFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab_my").setIndicator(tab_my), MyFragment.class, null);

		mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				ActionBar bar = getActionBar();
				switch (mTabHost.getCurrentTab()) {
				case TAB_MAIN:
					bar.setTitle("首页");
					break;
				case TAB_FIND:
					bar.setTitle("发现");
					break;
				case TAB_MY:
					bar.setTitle("我的");
					break;
				default:
					break;
				}
			}
		});
	}
}
