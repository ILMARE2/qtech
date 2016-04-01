package com.android.compus.fragement;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * ===============================
 *  ����: ��������: 
 *  ����ʱ�䣺2015��8��18�� ����3:34:18 
 *  �汾�ţ� 1.0
 *  ��Ȩ����(C) 2015��8��18�� 
 *  ������Fragment�ĸ��� 
 *  ===============================
 */
public abstract class BaseFragement extends Fragment {

	
	
	//������activity����
	public Activity mActivity;
	
	
	//Fragement ������ʱ�����
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity=getActivity();
	}
	
	//Fragement��Ӧ��view������ʱ��
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView();
	}
	
	//Fragement������activity���󴴽���ʱ�����
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		super.onActivityCreated(savedInstanceState);
	}


	
	//�����ֽ������ʵ��
	public abstract View initView() ;
	

	//���಻һ��ʵ��
	protected void initData() {
		
	}

	
}
