package com.android.compus.fragement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

import com.android.compus.LoginActivity;
import com.android.compus.MineInfoActivity;
import com.android.compus.MineSoftActivity;
import com.android.compus.OrderInfoActivity;
import com.android.compus.R;


public class LeftMenuFragement extends BaseFragement implements OnItemClickListener {

	//��Ӧ����Ŀ
	private String[] userItemNames = {"jingjingruta"} ;
	private String[] userItemContents = {""} ;
	private String[] orderItemNames = {"��ǰ����", "��ʷ����"};
	private String[] orderItemContents = {"", ""};
	private String[] aboutItemNames = {"֪ͨ����", "������", "�Ƽ�������", "�˳��˺�"};
	private String[] aboutItemContents = {"", "", "", ""};
	
	//��Ӧ��ͼƬid
	private int[] userImgIds = {R.drawable.ic_menu_myplaces};
	private int[] orderImgIds = {R.drawable.ic_menu_find_holo_light, R.drawable.ic_menu_copy_holo_light};
	private int[] aboutImgIds = {R.drawable.ic_menu_notifications, R.drawable.ic_menu_info_details, R.drawable.ic_menu_share, R.drawable.ic_star_yes};
	
	//����listView
	private ListView lvMineUser;
	private ListView lvMineOrder;
	private ListView lvMineAbout;
	
	//��Ӧ������������
	private MineListAdapter userListAdapter;
	private MineListAdapter orderListAdapter;
	private MineListAdapter aboutListAdapter;
	

	@Override
	public View initView() {
		
		View view=View.inflate(mActivity, R.layout.fragement_left_menu, null);
		//�ҵ��ؼ�
		lvMineUser = (ListView)view.findViewById(R.id.lv_mine_user);
		lvMineOrder = (ListView)view.findViewById(R.id.lv_mine_order);
		lvMineAbout = (ListView)view.findViewById(R.id.lv_mine_about);
		
		//��õ�ǰ��¼���û���
		userItemNames[0]=BmobUser.getCurrentUser(mActivity).getUsername();
		
		//����3��
		userListAdapter = new MineListAdapter(mActivity, userItemNames, userItemContents, userImgIds);
		orderListAdapter = new MineListAdapter(mActivity, orderItemNames, orderItemContents, orderImgIds);
		aboutListAdapter = new MineListAdapter(mActivity, aboutItemNames, aboutItemContents, aboutImgIds);
		
		//������ListView����������
		lvMineUser.setAdapter(userListAdapter);
		lvMineOrder.setAdapter(orderListAdapter);
		lvMineAbout.setAdapter(aboutListAdapter);
		
		lvMineUser.setOnItemClickListener(this);
		lvMineOrder.setOnItemClickListener(this);
		lvMineAbout.setOnItemClickListener(this);
		
		return view;
	}
	
	
	/**
	 * MineActivity ��Ŀ�б�������
	 */
	private class MineListAdapter extends BaseAdapter {
		
		private Context mContext;
		private String[] mItemNames; 		// ��Ŀ�б�����
		private String[] mItemContents;		//��Ŀ�б�ı�עֵ
		private int[] mItemImgIds;			// ��Ŀ�б�Icon	
		private LayoutInflater mInflater = null;
		
		
		public MineListAdapter(Context context, String[] names, String[] contents, int[] imgIds) {
			mContext = context;
			mItemNames = names;
			mItemContents = contents;
			mItemImgIds = imgIds;
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
				convertView = mInflater.inflate(R.layout.mine_list_item, null);
				holder = new MineListHolder();
				holder.imgItem = (ImageView) convertView.findViewById(R.id.img_item);
				holder.tvItemName = (TextView) convertView
						.findViewById(R.id.tv_item_name);
				holder.tvItemContent = (TextView) convertView.findViewById(R.id.tv_item_content);
				convertView.setTag(holder);
			} else {
				holder = (MineListHolder) convertView.getTag();
			}
			holder.imgItem.setBackgroundResource(mItemImgIds[position]);
			holder.tvItemName.setText(mItemNames[position]);
			holder.tvItemContent.setText(mItemContents[position]);
			return convertView;
		}

	}
	
	/**
	 * viewHolder
	 * @author zhangchenggeng
	 *
	 */
	public class MineListHolder {	
		public ImageView imgItem;		//��ĿIcon
		public TextView tvItemName;		//��Ŀ����
		public TextView tvItemContent;	//��Ŀֵ
	}

	

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			
			//��������
			if(parent.getId() == R.id.lv_mine_user) {
				switch (position) {
				case 0:		//���Ͽ�
					Intent toMineInfo = new Intent(mActivity, MineInfoActivity.class);
					startActivity(toMineInfo);
					break;
				default:
					break;
				}
			}
			
			//������Ϣ
			if(parent.getId() == R.id.lv_mine_order) {
				Intent toOrderInfo;
				switch (position) {
				case 0:          //��ǰ����
					toOrderInfo = new Intent(mActivity, OrderInfoActivity.class);
					toOrderInfo.putExtra("type", "now");
					startActivity(toOrderInfo);
					break;
				case 1:
					toOrderInfo = new Intent(mActivity, OrderInfoActivity.class);
					toOrderInfo.putExtra("type", "old");
					startActivity(toOrderInfo);
					break;
				default:
					break;
				}
			}
			
			//����
			if(parent.getId() == R.id.lv_mine_about) {
				switch (position) {
				case 1: 	//������
					Intent toMineSoft = new Intent(mActivity, MineSoftActivity.class);
					startActivity(toMineSoft);
					break;
				case 2:		//�Ƽ�������
					Intent toShare = new Intent(Intent.ACTION_SEND);
					toShare.setType("text/plain");
					toShare.putExtra(Intent.EXTRA_SUBJECT, "����");
					toShare.putExtra(Intent.EXTRA_TEXT, "hi,�Ƽ���ʹ��һ��������ǻ�У԰�ͻ��ˣ�");
					startActivity(Intent.createChooser(toShare, "����"));
					break;
				case 3:		                     
					BmobUser.logOut(mActivity);       //��ǰ�û��˳�,�˳�����½����
					Intent toLogin = new Intent(mActivity, LoginActivity.class);
					startActivity(toLogin);
					break;
				default:
					break;
				}
				
			}
			
		}
		

}
