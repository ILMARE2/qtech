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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ʧ������
 * ===============================
 */
public class LostFoundPager extends BasePager implements OnClickListener,
		OnItemClickListener {

	public RefreshListView lv_lost;   //����ˢ��ListView
	private List<Lost> mLosts;
	private View view;                //��������View
	private LostAdapter adapter;      //ʧ�������������

	int loadCount = 10;            // ÿ�μ���10��
	int currentCount = 0;          // Ŀǰ����
	boolean flag=false;
	
	public LostFoundPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initSuccessView() {
		// ��֮ǰ��view�Ƴ���
		if (view != null) {
			fl_content.removeView(view);
		}

		tv_title.setText("ʧ������");

		//���¼��ؽ���
		state=BasePager.STATE_LOADING;
		show();
		
		// ����Ӱ�ť����Ϊ�ɼ�
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
		query.setLimit(loadCount); // �������� ��ҳ��
		query.setSkip(currentCount); // ����ǰ��������ҳ��

		query.findObjects(mActivity, new FindListener<Lost>() {
			public void onSuccess(List<Lost> losts) {
				// �������ʾ���б���
				mLosts.addAll(losts);
				currentCount += losts.size();
				adapter.notifyDataSetChanged();

				if (losts.size() == 0) {
					Toast.makeText(mActivity, "û�и���������", 0).show();
				}
				lv_lost.onRefreshComplete(true);
			}

			public void onError(int code, String arg0) {
				Toast.makeText(mActivity, "��ȡ��Ϣʧ�ܣ�", 1).show();
				lv_lost.onRefreshComplete(false);
			}
		});
	}

	private void getDataFromServer() {
		
		
		BmobQuery<Lost> query = new BmobQuery<Lost>();
		// ����ʱ�併��
		query.order("-createdAt");

		query.setLimit(loadCount); // �������� ��ҳ��

		// ִ�в�ѯ����һ������Ϊ�����ģ��ڶ�������Ϊ���ҵĻص�
		query.findObjects(mActivity, new FindListener<Lost>() {
			public void onSuccess(List<Lost> losts) {
				if(!flag){
					// �������ʾ���б���
					state = STATE_SUCCESS;
					show();
					// ��FrameLayout�ж�̬���view
					fl_content.addView(view);
					
				}
				mLosts = losts;
				lv_lost.setAdapter(adapter);
				currentCount = losts.size();
				lv_lost.onRefreshComplete(true);
				
				flag=false;  //����Ϊfalse
			}

			public void onError(int code, String arg0) {
				state = STATE_ERROR;
				show();
				Toast.makeText(mActivity, "��ȡ��Ϣʧ�ܣ�", 1).show();
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

	// ��Ӱ�ť�ĵ���¼�
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
