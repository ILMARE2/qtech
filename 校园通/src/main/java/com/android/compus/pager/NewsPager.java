package com.android.compus.pager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.AddNewsActivity;
import com.android.compus.NewsActivity;
import com.android.compus.R;
import com.android.compus.bean.BXTNews;


/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：教务公告
 * ===============================
 */
public class NewsPager extends BasePager implements OnItemClickListener, OnClickListener {
	
	//新闻集合的ListView
	private ListView lvBXTNews;
	//新闻集合的数据
	private List<BXTNews> mBXTNewsList= new ArrayList<BXTNews>();
	//界面创建成功时的界面
	private View view;;
	
	//构造，要用到上下文
	public NewsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initSuccessView() {
		//如果view创建过一次，则移除掉先前的view
		if(view!=null){
			fl_content.removeView(view);
		}
		
		tv_title.setText("校内资讯");
		//将添加按钮设置为可见
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setOnClickListener(this);
		
		state=BasePager.STATE_LOADING;
		show();
		
		//创建显示的View对象
		view = View.inflate(mActivity, R.layout.pager_bxt, null);
		
		
		//从服务器拿到数据
		getDataFromServer();
		
		//设置条目点击事件
		lvBXTNews = (ListView)view.findViewById(R.id.lv_bxt_news);
		lvBXTNews.setOnItemClickListener(this);
	
		
		
		// 向FrameLayout中动态添加view
		fl_content.addView(view);
	
	}
	
	private void getDataFromServer() {
		
		SystemClock.sleep(500);
		
		//bmob的查询方法，查询出的内容直接封装成BXTNews对象
		BmobQuery<BXTNews> query = new BmobQuery<BXTNews>();
		query.findObjects(mActivity, new FindListener<BXTNews>() {  
			public void onSuccess(List<BXTNews> newsList) {
				if(newsList.size()==0){
					toast("暂无公告！");
				    
					state=STATE_EMPTY;
				    show();
				}else {
					state=STATE_SUCCESS;
					show();
					
					mBXTNewsList = newsList;
					//给ListView设置adapter
					lvBXTNews.setAdapter(new BXTListAdapter());
				}
				
			}
			
			public void onError(int arg0, String arg1) {
				state=STATE_ERROR;
				show();
				
			}
		});
	}

	private void toast(String toast) {
		Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
	}



	private  class BXTListAdapter extends BaseAdapter {
		
		public int getCount() {
			return mBXTNewsList.size();
		}

		public Object getItem(int position) {
			return mBXTNewsList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		// 刷新列表中的数据
		public void refresh(List<BXTNews> list) {
			mBXTNewsList = list;
			notifyDataSetChanged();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			BXTNewsHolder newsHolder;
			if (convertView == null) {
				convertView = View.inflate(mActivity,R.layout.bxt_list_item, null);
				newsHolder = new BXTNewsHolder();
				newsHolder.tvBXTNewsTitle = (TextView) convertView
						.findViewById(R.id.tv_bxt_news_item_title);
				convertView.setTag(newsHolder);
			} else {
				newsHolder = (BXTNewsHolder) convertView.getTag();
			}
			newsHolder.tvBXTNewsTitle.setText(mBXTNewsList.get(position).getTitle());
			return convertView;
		}

	}
	
	//新闻界面的holder
	public class BXTNewsHolder {
		public TextView tvBXTNewsTitle;  //博学堂讲座标题
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		BXTNews news=mBXTNewsList.get(position);
		Bundle bundle=new Bundle();
		bundle.putSerializable("news", news);
		Intent intent=new Intent(mActivity,NewsActivity.class);
		intent.putExtras(bundle);
		//时间要单独传过去
		intent.putExtra("time", news.getCreatedAt());
		
		mActivity.startActivity(intent);
	}


	public void onClick(View v) {
		//打开添加新闻界面
		Intent intent=new Intent(mActivity,AddNewsActivity.class);
		mActivity.startActivity(intent);
	}

}
