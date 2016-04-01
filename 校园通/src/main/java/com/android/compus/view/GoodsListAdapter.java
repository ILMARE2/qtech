package com.android.compus.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.compus.R;
import com.android.compus.bean.Good;

public class GoodsListAdapter extends BaseAdapter {

	private Context mContext;
	private List<Good> mGoodsList; // ��Ʒ�б���Ϣ
	private LayoutInflater mInflater = null;

	public GoodsListAdapter(Context context, List<Good> goodsList) {
		mContext = context;
		mGoodsList = goodsList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mGoodsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mGoodsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// ˢ���б��е�����
	public void refresh(List<Good> list) {
		mGoodsList = list;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GoodsHolder goodHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.goods_list_item, null);
			goodHolder = new GoodsHolder();
			goodHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_good_name);
			goodHolder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_good_price);
			goodHolder.btnBuyGood = (TextView) convertView
					.findViewById(R.id.btn_buy_good);
			convertView.setTag(goodHolder);
		} else {
			goodHolder = (GoodsHolder) convertView.getTag();
		}
		goodHolder.tvName.setText(mGoodsList.get(position).getName());
		goodHolder.tvPrice.setText("��"+mGoodsList.get(position).getPrice());
		return convertView;
	}
	
	/**
	 * ��Ʒ��ͼ
	 */
	public class GoodsHolder {
		
		public TextView tvName;   //��Ʒ����
		public TextView tvPrice;  //��Ʒ����
		public TextView btnBuyGood;  //����ť

	}


}
