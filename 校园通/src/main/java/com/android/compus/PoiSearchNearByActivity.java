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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��20�� ����7:09:43 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��20��
 * ������ 
 *  ===============================
 */
public class PoiSearchNearByActivity extends BaseActivity {

	private BaiduMap baiduMap;
	private MapView  mapView;   
	private String search_things;  //Ҫ���ҵ�����
	private PoiSearch poiSearch;   //���Ҷ���
	//��ʼ���ؼ�
	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_locate);
		mapView=(MapView) findViewById(R.id.mapview);
		baiduMap=mapView.getMap();	
		initMap();
		search_things=getIntent().getStringExtra("search_things");
	}

	private void initMap() {
		// ������ͼ״̬��Ҫ�����ı仯 ʹ�ù�����MapStatusUpdateFactory����
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// Ĭ�ϵļ���12
		// �������ż���
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
		nearbyOption.location(MyApplication.latlng);// �������ĵ�
		nearbyOption.radius(5000);// ���ð뾶��Χ5����   ��λ����
		nearbyOption.keyword(search_things);// �ؼ���
		
		poiSearch.searchNearby(nearbyOption);
		
	}

	class MyListener implements OnGetPoiSearchResultListener{
		//�õ���ϸ���
		public void onGetPoiDetailResult(PoiDetailResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "δ���������", 0).show();
				return;
			}
			
			String text = result.getAddress()+ "::" + result.getCommentNum() + result.getEnvironmentRating();
			Toast.makeText(getApplicationContext(), text, 0).show();
		}

		//�õ�poi����Ϣ
		public void onGetPoiResult(PoiResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "δ���������", 0).show();
				return;
			}
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// ����poi�ĸ�����
			baiduMap.setOnMarkerClickListener(overlay);// ���¼��ַ���overlay��overlay���ܴ������¼�
			overlay.setData(result);// ���ý��
			overlay.addToMap();// �������Ľ����ӵ���ͼ��
			overlay.zoomToSpan();// ���ŵ�ͼ��ʹ����Overlay���ں��ʵ���Ұ�� ע�� �÷���ֻ��Marker���͵�overlay��Ч
		}
		
		
	}
	
	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}
		
		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// �õ�������Ǹ�poi��Ϣ
			String text = poiInfo.name + "," + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			
			PoiDetailSearchOption detailOption = new PoiDetailSearchOption();
			detailOption.poiUid(poiInfo.uid);// ����poi��uid
			poiSearch.searchPoiDetail(detailOption);
			return super.onPoiClick(index);
		}
		
	}
}
