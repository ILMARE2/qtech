package com.android.compus;

import android.app.Activity;
import android.os.Bundle;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ����activity�Ļ���
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
