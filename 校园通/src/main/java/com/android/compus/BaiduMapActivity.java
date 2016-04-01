package com.android.compus;

import com.android.compus.application.MyApplication;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月20日 上午10:20:05 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月20日
 * 描述： 百度地图界面
 *  ===============================
 */
public class BaiduMapActivity extends BaseActivity {

	protected BaiduMap baiduMap; //百度地图
	protected MapView mapview;   //显示地图的控件
	
	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_locate);
		
		//找到地图控件
	    mapview = (MapView) findViewById(R.id.mapview);
		//拿到百度地图对象
		baiduMap=mapview.getMap();
				
		// 描述地图状态将要发生的变化 使用工厂类MapStatusUpdateFactory创建
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// 默认的级别12
		// 设置缩放级别
		baiduMap.setMapStatus(mapstatusUpdate);
		
		// LatLng latlng = new LatLng(arg0, arg1);// 坐标 经纬度 参数1 纬度 参数2 经度
		//MyApplication.latlng为定位得到的点
		MapStatusUpdate mapstatusUpdatePoint = MapStatusUpdateFactory
						.newLatLng(MyApplication.latlng);   
		// 设置中心点 
		baiduMap.setMapStatus(mapstatusUpdatePoint);
	}

	
}
