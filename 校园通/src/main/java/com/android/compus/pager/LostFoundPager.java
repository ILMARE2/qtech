package com.android.compus.pager;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.AddLostActivity;
import com.android.compus.R;
import com.android.compus.RegisterActivity;
import com.android.compus.bean.Lost;
import com.android.compus.view.RefreshListView;
import com.android.compus.view.RefreshListView.OnRefreshListener;
/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：失物招领
 * ===============================
 */
public class LostFoundPager extends BasePager implements OnClickListener,
		OnItemClickListener {

	public RefreshListView lv_lost;   //下拉刷新ListView
	private List<Lost> mLosts;
	private View view;                //填充的整个View
	private LostAdapter adapter;      //失物招领的适配器

	int loadCount = 10;            // 每次加载10个
	int currentCount = 0;          // 目前个数
	boolean flag=false;
	
	public LostFoundPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initSuccessView() {
		// 把之前的view移除掉
		if (view != null) {
			fl_content.removeView(view);
		}

		tv_title.setText("失物招领");

		//重新加载界面
		state=BasePager.STATE_LOADING;
		show();
		
		// 将添加按钮设置为可见
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setOnClickListener(this);

		view = View.inflate(mActivity, R.layout.pager_lost_found, null);
		lv_lost = (RefreshListView) view.findViewById(R.id.list_lost);

		adapter = new LostAdapter();
		lv_lost.setOnItemClickListener(this);

		lv_lost.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				flag=true;
				getDataFromServer();

			}

			@Override
			public void onLoadMore() {
				getMoreDataFromServer();
			}
		});

		getDataFromServer();

		

	}

	protected void showPopUpwindow(int position) {
		
	}
	
	
	protected void getMoreDataFromServer() {
	
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		query.order("-createdAt");
		query.setLimit(loadCount); // 设置限制 分页用
		query.setSkip(currentCount); // 跳过前两条，分页用

		query.findObjects(mActivity, new FindListener<Lost>() {
			public void onSuccess(List<Lost> losts) {
				// 将结果显示在列表中
				mLosts.addAll(losts);
				currentCount += losts.size();
				adapter.notifyDataSetChanged();

				if (losts.size() == 0) {
					Toast.makeText(mActivity, "没有更多数据了", 0).show();
				}
				lv_lost.onRefreshComplete(true);
			}

			public void onError(int code, String arg0) {
				Toast.makeText(mActivity, "获取信息失败！", 1).show();
				lv_lost.onRefreshComplete(false);
			}
		});
	}

	private void getDataFromServer() {
		
		
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		// 按照时间降序
		query.order("-createdAt");

		query.setLimit(loadCount); // 设置限制 分页用

		// 执行查询，第一个参数为上下文，第二个参数为查找的回调
		query.findObjects(mActivity, new FindListener<Lost>() {
			public void onSuccess(List<Lost> losts) {
				if(!flag){
					// 将结果显示在列表中
					state = STATE_SUCCESS;
					show();
					// 向FrameLayout中动态添加view
					fl_content.addView(view);
					
				}
				mLosts = losts;
				lv_lost.setAdapter(adapter);
				currentCount = losts.size();
				lv_lost.onRefreshComplete(true);
				
				flag=false;  //重置为false
			}

			public void onError(int code, String arg0) {
				state = STATE_ERROR;
				show();
				Toast.makeText(mActivity, "获取信息失败！", 1).show();
				lv_lost.onRefreshComplete(true);
			}
		});

	}

	private class LostAdapter extends BaseAdapter {

		public int getCount() {

			return mLosts.size();
		}

		public Object getItem(int position) {

			return mLosts.get(position);
		}

		public long getItemId(int position) {

			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				view = View.inflate(mActivity, R.layout.lost_found_item, null);
			} else {
				view = convertView;
			}
			TextView title = (TextView) view.findViewById(R.id.tv_title);
			TextView time = (TextView) view.findViewById(R.id.tv_time);
			TextView description = (TextView) view
					.findViewById(R.id.tv_describe);
			TextView photo = (TextView) view.findViewById(R.id.tv_photo);

			Lost lost = mLosts.get(position);
			title.setText(lost.getTitle());
			time.setText(lost.getCreatedAt());
			description.setText(lost.getDescribe());
			photo.setText(lost.getPhone());

			return view;
		}

	}

	// 添加按钮的点击事件
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add:
			Intent intent = new Intent(mActivity, AddLostActivity.class);
			mActivity.startActivityForResult(intent, 0);
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
