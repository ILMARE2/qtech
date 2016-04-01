package com.android.compus;

import android.widget.Toast;

import com.android.compus.application.MyApplication;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月20日 下午7:09:43 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月20日
 * 描述： 
 *  ===============================
 */
public class PoiSearchNearByActivity extends BaseActivity {

	private BaiduMap baiduMap;
	private MapView  mapView;   
	private String search_things;  //要查找的事物
	private PoiSearch poiSearch;   //查找对象
	//初始化控件
	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_locate);
		mapView=(MapView) findViewById(R.id.mapview);
		baiduMap=mapView.getMap();	
		initMap();
		search_things=getIntent().getStringExtra("search_things");
	}

	private void initMap() {
		// 描述地图状态将要发生的变化 使用工厂类MapStatusUpdateFactory创建
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// 默认的级别12
		// 设置缩放级别
		baiduMap.setMapStatus(mapstatusUpdate);
	}

	protected void initData() {
		
		super.initData();
		serach();
	}

	private void serach() {
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(new MyListener());
		
		PoiNearbySearchOption nearbyOption = new PoiNearbySearchOption();
		nearbyOption.location(MyApplication.latlng);// 设置中心点
		nearbyOption.radius(5000);// 设置半径周围5公里   单位是米
		nearbyOption.keyword(search_things);// 关键字
		
		poiSearch.searchNearby(nearbyOption);
		
	}

	class MyListener implements OnGetPoiSearchResultListener{
		//拿到详细结果
		public void onGetPoiDetailResult(PoiDetailResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
				return;
			}
			
			String text = result.getAddress()+ "::" + result.getCommentNum() + result.getEnvironmentRating();
			Toast.makeText(getApplicationContext(), text, 0).show();
		}

		//拿到poi的信息
		public void onGetPoiResult(PoiResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "未搜索到结果", 0).show();
				return;
			}
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// 搜索poi的覆盖物
			baiduMap.setOnMarkerClickListener(overlay);// 把事件分发给overlay，overlay才能处理点击事件
			overlay.setData(result);// 设置结果
			overlay.addToMap();// 把搜索的结果添加到地图中
			overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注： 该方法只对Marker类型的overlay有效
		}
		
		
	}
	
	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}
		
		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// 得到点击的那个poi信息
			String text = poiInfo.name + "," + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			
			PoiDetailSearchOption detailOption = new PoiDetailSearchOption();
			detailOption.poiUid(poiInfo.uid);// 设置poi的uid
			poiSearch.searchPoiDetail(detailOption);
			return super.onPoiClick(index);
		}
		
	}
}
