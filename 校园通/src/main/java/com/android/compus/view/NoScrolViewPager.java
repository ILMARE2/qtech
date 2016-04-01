package com.android.compus.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 
 * 自定义控件，去除viewpager的滑动效果
 * @author zhangchenggeng
 * @date 2015 8 3
 *
 */
public class NoScrolViewPager extends ViewPager {

	
	
	public NoScrolViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	public NoScrolViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	//重写onTouchEvent，什么都不做可以去掉viewpager的滑动事件
	public boolean onTouchEvent(MotionEvent arg0) {
		return true;
	}

	/**
	 * 表示事件是否拦截，返回false表示不拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
