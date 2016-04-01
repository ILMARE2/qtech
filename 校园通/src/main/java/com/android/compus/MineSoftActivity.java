package com.android.compus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class MineSoftActivity extends Activity implements OnItemClickListener{
	
	
	private String[] softItemNames = {"意见反馈", "检查更新", "使用协议", "关于我们"};
	private String[] softItemContents = {"", "", "", ""};
	private ListView lvMineSoft;
	
	private MineSoftAdapter softListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft);
		
		initView();
	}
	
	private void initView() {
		lvMineSoft = (ListView) findViewById(R.id.lv_mine_soft);
		softListAdapter = new MineSoftAdapter(this, softItemNames, softItemContents);
		lvMineSoft.setAdapter(softListAdapter);
		lvMineSoft.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0: 
			Intent toFeedBack = new Intent(MineSoftActivity.this, FeedBackActivity.class);
			startActivity(toFeedBack);
			break;
		case 1:
			toast("已经是最新版本");
			break;
		case 2:
			break;
		case 3:
//			Intent toAboutSoft = new Intent(MineSoftActivity.this, AboutActivity.class);
//			startActivity(toAboutSoft);
			break;

		default:
			break;
		}
		
	}
	
	public void clickSoftBack(View v) {
		finish();
	}
	
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * MineActivity 项目列表适配器
	 * @date 2014-5-10
	 * @author Stone
	 */
	public class MineSoftAdapter extends BaseAdapter {
		
		private Context mContext;
		private String[] mItemNames; 		// 项目列表名称
		private String[] mItemContents;		//项目列表的备注值
		private LayoutInflater mInflater = null;
		
		public MineSoftAdapter(Context context, String[] names, String[] contents) {
			mContext = context;
			mItemNames = names;
			mItemContents = contents;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mItemNames.length;
		}

		@Override
		public Object getItem(int position) {
			return mItemNames[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MineListHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.mine_soft_list_item, null);
				holder = new MineListHolder();
				holder.tvItemName = (TextView) convertView
						.findViewById(R.id.tv_item_name);
				holder.tvItemContent = (TextView) convertView.findViewById(R.id.tv_item_content);
				convertView.setTag(holder);
			} else {
				holder = (MineListHolder) convertView.getTag();
			}
			holder.tvItemName.setText(mItemNames[position]);
			holder.tvItemContent.setText(mItemContents[position]);
			return convertView;
		}

	}

	
	public class MineListHolder {
		
		public ImageView imgItem;		//项目Icon
		public TextView tvItemName;		//项目名称
		public TextView tvItemContent;	//项目值
		
	}
}
