package com.android.compus.fragement;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * ===============================
 *  作者: 静静茹她: 
 *  创建时间：2015年8月18日 下午3:34:18 
 *  版本号： 1.0
 *  版权所有(C) 2015年8月18日 
 *  描述：Fragment的父类 
 *  ===============================
 */
public abstract class BaseFragement extends Fragment {

	
	
	//依附的activity对象
	public Activity mActivity;
	
	
	//Fragement 创建的时候调用
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity=getActivity();
	}
	
	//Fragement对应的view创建的时候
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView();
	}
	
	//Fragement依附的activity对象创建的时候调用
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		super.onActivityCreated(savedInstanceState);
	}


	
	//所有字界面必须实现
	public abstract View initView() ;
	

	//子类不一定实现
	protected void initData() {
		
	}

	
}
