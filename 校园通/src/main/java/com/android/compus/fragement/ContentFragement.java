package com.android.compus.fragement;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.compus.R;
import com.android.compus.pager.BasePager;
import com.android.compus.pager.HomePager;
import com.android.compus.pager.LostFoundPager;
import com.android.compus.pager.NewsPager;
import com.android.compus.pager.SecondGoodPager;
import com.android.compus.pager.ToolsPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentFragement extends BaseFragement implements OnClickListener {


	@ViewInject(R.id.btn_home)
	private LinearLayout btn_home;        //主页
	
	@ViewInject(R.id.btn_news)
	private LinearLayout btn_news;        //教务公告
	
	@ViewInject(R.id.btn_lostfound)
	private LinearLayout btn_lostfound;   //事物招领 
	
	@ViewInject(R.id.btn_secondhands)
	private LinearLayout btn_secondhands; //二手交易
	
	@ViewInject(R.id.btn_tools)
	private LinearLayout btn_tools;       //实用工具

	@ViewInject(R.id.vp_content)
	private ViewPager vp_content;         // 中间内容的ViewPager

	
	public ArrayList<BasePager> viewList;
	private HomePager homePager;

	
	//父类中oncreteView
	public View initView() {
		
		View view = View.inflate(mActivity, R.layout.fragement_content, null);
		//初始化各种控件
		ViewUtils.inject(this, view);
		//设置监听
		setListener();
		return view;
	}

	private void setListener() {
		// 设置监听
		btn_home.setOnClickListener(this);
		btn_lostfound.setOnClickListener(this);
		btn_news.setOnClickListener(this);
		btn_secondhands.setOnClickListener(this);
		btn_tools.setOnClickListener(this);
	}

	// 初始化数据
	protected void initData() {
		
		//初始化数据
		homePager = new HomePager(mActivity);
		viewList = new ArrayList<BasePager>();
		viewList.add(homePager);
		viewList.add(new NewsPager(mActivity));
		viewList.add(new SecondGoodPager(mActivity));
		viewList.add(new LostFoundPager(mActivity));
		viewList.add(new ToolsPager(mActivity));
		
		// 设置适配器
		vp_content.setAdapter(new MyAdapter());

		// 设置选中第1页
		vp_content.setCurrentItem(0);
		
		//初始化第一页的数据
		viewList.get(0).initSuccessView();
		
		//设置颜色
		btn_home.setBackgroundColor(0xff8AC8E4);
		
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				//页面选中后初始化数据
				viewList.get(arg0).initSuccessView();
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	
	//点击事件
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			changeColor(btn_home);
			vp_content.setCurrentItem(0,false);
			break;
		case R.id.btn_news:
			changeColor(btn_news);
			vp_content.setCurrentItem(1,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_secondhands:
			changeColor(btn_secondhands);
			vp_content.setCurrentItem(2,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_lostfound:
			changeColor(btn_lostfound);
			vp_content.setCurrentItem(3,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_tools:
			changeColor(btn_tools);
			vp_content.setCurrentItem(4,false);
			homePager.isRunning=false;
			break;
		default:
			break;
		}
	}

	private void changeColor(LinearLayout ll_selector) {
		
		//将所有颜色初始化
		btn_home.setBackgroundColor(0xffE4E1E4);
		btn_lostfound.setBackgroundColor(0xffE4E1E4);
		btn_news.setBackgroundColor(0xffE4E1E4);
		btn_secondhands.setBackgroundColor(0xffE4E1E4);
		btn_tools.setBackgroundColor(0xffE4E1E4);
		//改变目标控件颜色
		ll_selector.setBackgroundColor(0xff8AC8E4);;
	}

	// ViewPager的
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position).rootview);
			return viewList.get(position).rootview;
		}

	}

}
