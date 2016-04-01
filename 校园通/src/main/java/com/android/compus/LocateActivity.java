package com.android.compus;

import android.os.Bundle;

import com.android.compus.application.MyApplication;
import com.android.compus.utils.SharePreferencesUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class LocateActivity extends BaseActivity{
	protected BaiduMap baiduMap; //百度地图
	protected MapView mapview;   //显示地图的控件
	
	public LocationClient mLocationClient;  //定位器
	public BDLocationListener myListener;  //定位的监听器
	
	protected void initData() {
		lacate();
		super.initData();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_locate);
		init();
		super.initView();
	}
	private void lacate() {
		mLocationClient = new LocationClient(getApplicationContext());
		myListener = new MyListener();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
			
		MyLocationConfiguration configuration = new MyLocationConfiguration(
				MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
		baiduMap.setMyLocationConfigeration(configuration);// 设置定位显示的模式
		baiduMap.setMyLocationEnabled(true);// 打开定位图层
	}

	private void init() {
		//找到地图控件
		mapview = (MapView) findViewById(R.id.mapview);
		//拿到百度地图对象
		baiduMap=mapview.getMap();
		// 描述地图状态将要发生的变化 使用工厂类MapStatusUpdateFactory创建
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// 默认的级别12
		// 设置缩放级别
		baiduMap.setMapStatus(mapstatusUpdate);
	}
	
	class MyListener implements BDLocationListener {
		public void onReceiveLocation(BDLocation result) {
			if (result != null) {
				MyLocationData data = new MyLocationData.Builder()
						.latitude(result.getLatitude())
						.longitude(result.getLongitude()).build();
				baiduMap.setMyLocationData(data);
				//设置当前位置
				MyApplication.latlng=new LatLng(result.getLatitude(), result.getLongitude());
				SharePreferencesUtils.setString(getApplicationContext(), "latitude", result.getLatitude()+"");
				SharePreferencesUtils.setString(getApplicationContext(), "longitude", result.getLongitude()+"");
			}
		}

	}
	
	protected void onDestroy() {
		mapview.onDestroy();
		
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		mLocationClient.start();
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		mapview.onResume();
		
		super.onResume();
	}

	@Override
	protected void onPause() {
		mLocationClient.stop();
		mapview.onPause();
		super.onPause();
	}
	
}
