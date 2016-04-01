package com.android.compus;

import android.app.Activity;
import android.os.Bundle;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 所有activity的基类
 *  ===============================
 */
public class BaseActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		setListener();
		initData();
	}
	
	
	protected void initView() {
		
	}
	
	protected void setListener() {
		
	}
	
	protected void initData() {
		
	}
	
	
	
}
