package com.android.compus.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 
 * �Զ���ؼ���ȥ��viewpager�Ļ���Ч��
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
	
	
	//��дonTouchEvent��ʲô����������ȥ��viewpager�Ļ����¼�
	public boolean onTouchEvent(MotionEvent arg0) {
		return true;
	}

	/**
	 * ��ʾ�¼��Ƿ����أ�����false��ʾ������
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
