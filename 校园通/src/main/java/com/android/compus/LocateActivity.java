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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class LocateActivity extends BaseActivity{
	protected BaiduMap baiduMap; //�ٶȵ�ͼ
	protected MapView mapview;   //��ʾ��ͼ�Ŀؼ�
	
	public LocationClient mLocationClient;  //��λ��
	public BDLocationListener myListener;  //��λ�ļ�����
	
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
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);// ���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);
			
		MyLocationConfiguration configuration = new MyLocationConfiguration(
				MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
		baiduMap.setMyLocationConfigeration(configuration);// ���ö�λ��ʾ��ģʽ
		baiduMap.setMyLocationEnabled(true);// �򿪶�λͼ��
	}

	private void init() {
		//�ҵ���ͼ�ؼ�
		mapview = (MapView) findViewById(R.id.mapview);
		//�õ��ٶȵ�ͼ����
		baiduMap=mapview.getMap();
		// ������ͼ״̬��Ҫ�����ı仯 ʹ�ù�����MapStatusUpdateFactory����
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// Ĭ�ϵļ���12
		// �������ż���
		baiduMap.setMapStatus(mapstatusUpdate);
	}
	
	class MyListener implements BDLocationListener {
		public void onReceiveLocation(BDLocation result) {
			if (result != null) {
				MyLocationData data = new MyLocationData.Builder()
						.latitude(result.getLatitude())
						.longitude(result.getLongitude()).build();
				baiduMap.setMyLocationData(data);
				//���õ�ǰλ��
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
