package com.android.compus.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

import com.android.compus.utils.SharePreferencesUtils;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ȫ�ֵ�Application���
 * 		 ��������Ӧ��ƽ̨�ĳ�ʼ���͹�������
 *  ===============================
 */

public class MyApplication extends Application {
	
	//��¼��ǰλ��
	public static double latitude =36.680745;      //Ĭ�ϵ��Ǽ���ĳ��
	public static double longitude=117.031197;
	public static LatLng latlng;                 //�����ʼ��ʱ����һ��ȫ��λ����Ϣ��������������
	                                             //�Ǵ������ļ��ж�ȡ�����ĵ�ǰλ����Ϣ
	public void onCreate() {
		super.onCreate();
		
		//�������ļ��ж�ȡ��ǰλ����Ϣ��û�еĻ�����Ĭ�ϵļ��ϵ�����
		latitude= Double.parseDouble(SharePreferencesUtils.getString(getApplicationContext(), "latitude", "36.680745"));// γ��
		longitude =Double.parseDouble(SharePreferencesUtils.getString(getApplicationContext(), "longitude", "117.031197"));// γ��;// ����
		latlng = new LatLng(latitude, longitude);
		
		//Bmob�ƶ��Ʒ���ƽ̨��-- У԰ͨ
		//Bmob.initialize(getApplicationContext(), "9985738d1e7b7ea22c17bc35bfd3c755");
		
		//Bmob��ʼ��--��������
		Bmob.initialize(getApplicationContext(), "c01b2c77757b9984c82450bf36b45806");
		
		
		//�ٶȵ�ͼ��ʼ��---У԰ͨ
		SDKInitializer.initialize(getApplicationContext()); // ���ܴ���Activity������ȫ��Context
		
		//�ٶȵ�ͼע�����
		MyBaduSdkReceiver baduSdkReceiver = new MyBaduSdkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);// ע���������
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR); // ע��keyУ����
		registerReceiver(baduSdkReceiver, filter);
		
		// ��ʼ����������---
		//SpeechUtility.createUtility(this, SpeechConstant.APPID + "=54b8bca3");//ʾ������
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=55d2ccfa"); //�Լ�Ӧ��
		
		
	}
	
	//�����ٶȵ�ͼ��ʼ��
	class MyBaduSdkReceiver extends BroadcastReceiver {
	    public void onReceive(Context context, Intent intent) {
	        String result = intent.getAction();
	        if (result
	                .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
	            // �������
	            Toast.makeText(getApplicationContext(), "������", 0).show();
	        } else if (result
	                .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
	            // keyУ��ʧ��
	            Toast.makeText(getApplicationContext(), "У��ʧ��", 0).show();
	        }
	    }

	}

	
}
