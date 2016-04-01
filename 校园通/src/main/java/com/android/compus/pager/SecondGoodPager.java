package com.android.compus.pager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.AddSecondGoodActivity;
import com.android.compus.R;
import com.android.compus.SecondGoodDetailActivity;
import com.android.compus.bean.SecondGood;
import com.android.compus.view.RefreshListView;
import com.android.compus.view.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：二手市场
 * ===============================
 */
public class SecondGoodPager extends BasePager implements OnClickListener, OnItemClickListener {

	public RefreshListView list_secondGood;
	public List<SecondGood> goodList = new ArrayList<SecondGood>();
	private View view;
	private SecondGoodAdapter adpter;
	private int loadCount=10;    //每次加载的数量
	private int currentCount=0;  //当前加载到的数量
	private boolean flag=false;
	
	public SecondGoodPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initSuccessView() {
		if(view!=null){
			fl_content.removeView(view);
		}
		
		tv_title.setText("二手交易");
		// 将添加按钮设置为可见
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setOnClickListener(this);
		
		//重新加载页面
		state=BasePager.STATE_LOADING;
		show();
		
		//创建用于显示的View
		view = View.inflate(mActivity, R.layout.pager_second_good, null);
		list_secondGood = (RefreshListView) view.findViewById(R.id.list_secondGood);
		
		//条目的点击事件
		list_secondGood.setOnItemClickListener(this); 
		
		list_secondGood.setOnRefreshListener(new OnRefreshListener() {
			//下拉刷新
			public void onRefresh() {
				
				flag=true;
				getDataFromServer();
			}
			//加载更多
			public void onLoadMore() {
				
				getMoreDataFromServer();
			}
		});
		
		// 从服务器获取数据
		getDataFromServer();
	}

	protected void getMoreDataFromServer() {
		
		BmobQuery<SecondGood> query = new BmobQuery<SecondGood>();
		// 按照时间降序
		query.order("-createdAt");
		query.setLimit(loadCount);   //加载前10条
		query.setSkip(currentCount); //跳过多少条
		
		query.findObjects(mActivity, new FindListener<SecondGood>() {
			public void onSuccess(List<SecondGood> goods) {
				// 将结果显示在列表中
				 goodList .addAll(goods) ;
				 currentCount+=goods.size();
				 adpter.notifyDataSetChanged();
				 list_secondGood.onRefreshComplete(true);
				 if(goods.size()==0){
					 Toast.makeText(mActivity, "已无更多数据", 0).show();
				 }
			}
			public void onError(int code, String arg0) {
				Toast.makeText(mActivity, "获取信息失败！", 1).show();
				list_secondGood.onRefreshComplete(false);
			}
		});

	}

	private void getDataFromServer() {
	
		BmobQuery<SecondGood> query = new BmobQuery<SecondGood>();
		// 按照时间降序
		query.order("-createdAt");
		
		query.setLimit(loadCount);   //加载前10条
		
		// 执行查询，第一个参数为上下文，第二个参数为查找的回调
		query.findObjects(mActivity, new FindListener<SecondGood>() {
			public void onSuccess(List<SecondGood> goods) {
				if(!flag){
					// 将结果显示在列表中
					state=STATE_SUCCESS;
					show();
					// 向FrameLayout中动态添加view
					fl_content.addView(view);
					
				}
				 goodList = goods;
				 adpter=new SecondGoodAdapter();
				 list_secondGood.setAdapter(adpter);
				 currentCount=goods.size();
				 list_secondGood.onRefreshComplete(true);
				 flag=false;
			}

			public void onError(int code, String arg0) {
				state=STATE_ERROR;
				show();
				Toast.makeText(mActivity, "获取信息失败！", 1).show();
				list_secondGood.onRefreshComplete(false);
			}
		});

	}

	/**
	 * 
	 * @author zhangchenggeng
	 *
	 */
	public class SecondGoodAdapter extends BaseAdapter {


		private BitmapUtils utils;

		public SecondGoodAdapter(){
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.ic_launcher);
		}
		
		public int getCount() {

			return goodList.size();
		}

		public Object getItem(int position) {

			return goodList.get(position);
		}

		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=null;
			if(convertView==null){
				view=View.inflate(mActivity, R.layout.second_good_item, null);
			}else{
				view=convertView;
			}
			TextView title=(TextView) view.findViewById(R.id.tv_title);
			TextView time =(TextView) view.findViewById(R.id.tv_time);
			TextView description =(TextView) view.findViewById(R.id.tv_describe);
			TextView photo =(TextView) view.findViewById(R.id.tv_photo);
			TextView price=(TextView) view.findViewById(R.id.tv_price);
			final ImageView iv_image=(ImageView) view.findViewById(R.id.iv_image);
			SecondGood secondGood=goodList.get(position);
			

			title.setText(secondGood.getTitle());
			time.setText(secondGood.getCreatedAt());
			description.setText(secondGood.getDescription());
			photo.setText(secondGood.getPhone());
			price.setText("¥"+secondGood.getPrice());
			final BmobFile bmobFile=secondGood.getImage();
			if(bmobFile!=null){
				iv_image.setVisibility(View.VISIBLE);
				utils.display(iv_image, bmobFile.getFileUrl());
			}else{
				iv_image.setVisibility(View.GONE);
				
			}
			
			return view;
		}

	}

	public class SecondGoodHolder{
		TextView title ;
		TextView time ;
		TextView description ;
		TextView photo;
		TextView price;
		ImageView iv_image ;
	}
	
	//添加按钮点击事件
	public void onClick(View v) {
		Intent intent=new Intent(mActivity,AddSecondGoodActivity.class);
		mActivity.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

			Intent intent=new Intent(mActivity,SecondGoodDetailActivity.class);
			SecondGood secondGood=(SecondGood) goodList.get(position);
			intent.putExtra("time", secondGood.getCreatedAt());
			intent.putExtra("title", secondGood.getTitle());
			intent.putExtra("price", secondGood.getPrice());
			intent.putExtra("phone", secondGood.getPhone());
			intent.putExtra("description", secondGood.getDescription());
			
			BmobFile bmobFile=secondGood.getImage();
			if(bmobFile!=null){
				intent.putExtra("imageUrl", secondGood.getImage().getFileUrl());
			}else{
				intent.putExtra("imageUrl", "");
			}
			mActivity.startActivity(intent);
			
		}
		
	
		
	

}
