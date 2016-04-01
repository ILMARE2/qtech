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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ���������񹫸�
 * ===============================
 */
public class NewsPager extends BasePager implements OnItemClickListener, OnClickListener {
	
	//���ż��ϵ�ListView
	private ListView lvBXTNews;
	//���ż��ϵ�����
	private List<BXTNews> mBXTNewsList= new ArrayList<BXTNews>();
	//���洴���ɹ�ʱ�Ľ���
	private View view;;
	
	//���죬Ҫ�õ�������
	public NewsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initSuccessView() {
		//���view������һ�Σ����Ƴ�����ǰ��view
		if(view!=null){
			fl_content.removeView(view);
		}
		
		tv_title.setText("У����Ѷ");
		//����Ӱ�ť����Ϊ�ɼ�
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setOnClickListener(this);
		
		state=BasePager.STATE_LOADING;
		show();
		
		//������ʾ��View����
		view = View.inflate(mActivity, R.layout.pager_bxt, null);
		
		
		//�ӷ������õ�����
		getDataFromServer();
		
		//������Ŀ����¼�
		lvBXTNews = (ListView)view.findViewById(R.id.lv_bxt_news);
		lvBXTNews.setOnItemClickListener(this);
	
		
		
		// ��FrameLayout�ж�̬���view
		fl_content.addView(view);
	
	}
	
	private void getDataFromServer() {
		
		SystemClock.sleep(500);
		
		//bmob�Ĳ�ѯ��������ѯ��������ֱ�ӷ�װ��BXTNews����
		BmobQuery<BXTNews> query = new BmobQuery<BXTNews>();
		query.findObjects(mActivity, new FindListener<BXTNews>() {  
			public void onSuccess(List<BXTNews> newsList) {
				if(newsList.size()==0){
					toast("���޹��棡");
				    
					state=STATE_EMPTY;
				    show();
				}else {
					state=STATE_SUCCESS;
					show();
					
					mBXTNewsList = newsList;
					//��ListView����adapter
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

		// ˢ���б��е�����
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
	
	//���Ž����holder
	public class BXTNewsHolder {
		public TextView tvBXTNewsTitle;  //��ѧ�ý�������
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		BXTNews news=mBXTNewsList.get(position);
		Bundle bundle=new Bundle();
		bundle.putSerializable("news", news);
		Intent intent=new Intent(mActivity,NewsActivity.class);
		intent.putExtras(bundle);
		//ʱ��Ҫ��������ȥ
		intent.putExtra("time", news.getCreatedAt());
		
		mActivity.startActivity(intent);
	}


	public void onClick(View v) {
		//��������Ž���
		Intent intent=new Intent(mActivity,AddNewsActivity.class);
		mActivity.startActivity(intent);
	}

}
