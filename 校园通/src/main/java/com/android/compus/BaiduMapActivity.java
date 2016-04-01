package com.android.compus;

import com.android.compus.application.MyApplication;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��20�� ����10:20:05 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��20��
 * ������ �ٶȵ�ͼ����
 *  ===============================
 */
public class BaiduMapActivity extends BaseActivity {

	protected BaiduMap baiduMap; //�ٶȵ�ͼ
	protected MapView mapview;   //��ʾ��ͼ�Ŀؼ�
	
	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_locate);
		
		//�ҵ���ͼ�ؼ�
	    mapview = (MapView) findViewById(R.id.mapview);
		//�õ��ٶȵ�ͼ����
		baiduMap=mapview.getMap();
				
		// ������ͼ״̬��Ҫ�����ı仯 ʹ�ù�����MapStatusUpdateFactory����
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// Ĭ�ϵļ���12
		// �������ż���
		baiduMap.setMapStatus(mapstatusUpdate);
		
		// LatLng latlng = new LatLng(arg0, arg1);// ���� ��γ�� ����1 γ�� ����2 ����
		//MyApplication.latlngΪ��λ�õ��ĵ�
		MapStatusUpdate mapstatusUpdatePoint = MapStatusUpdateFactory
						.newLatLng(MyApplication.latlng);   
		// �������ĵ� 
		baiduMap.setMapStatus(mapstatusUpdatePoint);
	}

	
}
