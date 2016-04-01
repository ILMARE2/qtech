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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 全局的Application组件
 * 		 包含各个应用平台的初始化和公共数据
 *  ===============================
 */

public class MyApplication extends Application {
	
	//记录当前位置
	public static double latitude =36.680745;      //默认点是济南某地
	public static double longitude=117.031197;
	public static LatLng latlng;                 //程序初始化时保存一个全局位置信息，给其他界面用
	                                             //是从配置文件中读取出来的当前位置信息
	public void onCreate() {
		super.onCreate();
		
		//从配置文件中读取当前位置信息，没有的画就用默认的济南的坐标
		latitude= Double.parseDouble(SharePreferencesUtils.getString(getApplicationContext(), "latitude", "36.680745"));// 纬度
		longitude =Double.parseDouble(SharePreferencesUtils.getString(getApplicationContext(), "longitude", "117.031197"));// 纬度;// 经度
		latlng = new LatLng(latitude, longitude);
		
		//Bmob移动云服务平台的-- 校园通
		//Bmob.initialize(getApplicationContext(), "9985738d1e7b7ea22c17bc35bfd3c755");
		
		//Bmob初始化--静静茹她
		Bmob.initialize(getApplicationContext(), "c01b2c77757b9984c82450bf36b45806");
		
		
		//百度地图初始化---校园通
		SDKInitializer.initialize(getApplicationContext()); // 不能传递Activity，须是全局Context
		
		//百度地图注册监听
		MyBaduSdkReceiver baduSdkReceiver = new MyBaduSdkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);// 注册网络错误
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR); // 注册key校验结果
		registerReceiver(baduSdkReceiver, filter);
		
		// 初始化语音引擎---
		//SpeechUtility.createUtility(this, SpeechConstant.APPID + "=54b8bca3");//示例程序
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=55d2ccfa"); //自己应用
		
		
	}
	
	//监听百度地图初始化
	class MyBaduSdkReceiver extends BroadcastReceiver {
	    public void onReceive(Context context, Intent intent) {
	        String result = intent.getAction();
	        if (result
	                .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
	            // 网络错误
	            Toast.makeText(getApplicationContext(), "无网络", 0).show();
	        } else if (result
	                .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
	            // key校验失败
	            Toast.makeText(getApplicationContext(), "校验失败", 0).show();
	        }
	    }

	}

	
}
