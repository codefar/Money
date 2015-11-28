package com.example.money;

import com.example.money.widget.DashBoard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {

	private TextView mBuyTextView;
	private TextView mTenderTitleTextView;
	private TextView mRateTextView;
	private TextView mExtraRateTextView;
	private View mExtraRateLayout;
	private TextView mPeriodTextView;
	private TextView mAtLeastTextView;
	private TextView mAvailableTextView;
	private TextView mDayTextView;
	private TextView mHourTextView;
	private TextView mMinuteTextView;
	private TextView mSecondTextView;
	private DashBoard mDashBoard;

	public MainFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		
		mTenderTitleTextView = (TextView) view.findViewById(R.id.tender_title);//标的title
		mRateTextView = (TextView) view.findViewById(R.id.rate);//利率
		mExtraRateLayout = view.findViewById(R.id.extra_rate_layout);//加息layout
		mExtraRateTextView = (TextView) view.findViewById(R.id.extra_rate);//加息
		mPeriodTextView = (TextView) view.findViewById(R.id.period);//期限
		mAtLeastTextView = (TextView) view.findViewById(R.id.at_least);//起投金额
		mAvailableTextView = (TextView) view.findViewById(R.id.available);//可投金额
		mDayTextView = (TextView) view.findViewById(R.id.day);//天
		mHourTextView = (TextView) view.findViewById(R.id.hour);//时
		mMinuteTextView = (TextView) view.findViewById(R.id.minute);//分
		mSecondTextView = (TextView) view.findViewById(R.id.second);//秒
		mDashBoard = (DashBoard) view.findViewById(R.id.progress);//标的进度
		
		mBuyTextView = (TextView) view.findViewById(R.id.tail);//立即抢购
		return view;
	}
}
