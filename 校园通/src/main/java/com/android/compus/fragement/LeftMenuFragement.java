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

	//对应的条目
	private String[] userItemNames = {"jingjingruta"} ;
	private String[] userItemContents = {""} ;
	private String[] orderItemNames = {"当前订单", "历史订单"};
	private String[] orderItemContents = {"", ""};
	private String[] aboutItemNames = {"通知中心", "软件相关", "推荐给朋友", "退出账号"};
	private String[] aboutItemContents = {"", "", "", ""};
	
	//对应的图片id
	private int[] userImgIds = {R.drawable.ic_menu_myplaces};
	private int[] orderImgIds = {R.drawable.ic_menu_find_holo_light, R.drawable.ic_menu_copy_holo_light};
	private int[] aboutImgIds = {R.drawable.ic_menu_notifications, R.drawable.ic_menu_info_details, R.drawable.ic_menu_share, R.drawable.ic_star_yes};
	
	//三个listView
	private ListView lvMineUser;
	private ListView lvMineOrder;
	private ListView lvMineAbout;
	
	//对应的三个适配器
	private MineListAdapter userListAdapter;
	private MineListAdapter orderListAdapter;
	private MineListAdapter aboutListAdapter;
	

	@Override
	public View initView() {
		
		View view=View.inflate(mActivity, R.layout.fragement_left_menu, null);
		//找到控件
		lvMineUser = (ListView)view.findViewById(R.id.lv_mine_user);
		lvMineOrder = (ListView)view.findViewById(R.id.lv_mine_order);
		lvMineAbout = (ListView)view.findViewById(R.id.lv_mine_about);
		
		//获得当前登录的用户名
		userItemNames[0]=BmobUser.getCurrentUser(mActivity).getUsername();
		
		//创建3个
		userListAdapter = new MineListAdapter(mActivity, userItemNames, userItemContents, userImgIds);
		orderListAdapter = new MineListAdapter(mActivity, orderItemNames, orderItemContents, orderImgIds);
		aboutListAdapter = new MineListAdapter(mActivity, aboutItemNames, aboutItemContents, aboutImgIds);
		
		//给三个ListView设置适配器
		lvMineUser.setAdapter(userListAdapter);
		lvMineOrder.setAdapter(orderListAdapter);
		lvMineAbout.setAdapter(aboutListAdapter);
		
		lvMineUser.setOnItemClickListener(this);
		lvMineOrder.setOnItemClickListener(this);
		lvMineAbout.setOnItemClickListener(this);
		
		return view;
	}
	
	
	/**
	 * MineActivity 项目列表适配器
	 */
	private class MineListAdapter extends BaseAdapter {
		
		private Context mContext;
		private String[] mItemNames; 		// 项目列表名称
		private String[] mItemContents;		//项目列表的备注值
		private int[] mItemImgIds;			// 项目列表Icon	
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
		public ImageView imgItem;		//项目Icon
		public TextView tvItemName;		//项目名称
		public TextView tvItemContent;	//项目值
	}

	

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			
			//个人资料
			if(parent.getId() == R.id.lv_mine_user) {
				switch (position) {
				case 0:		//资料卡
					Intent toMineInfo = new Intent(mActivity, MineInfoActivity.class);
					startActivity(toMineInfo);
					break;
				default:
					break;
				}
			}
			
			//订单信息
			if(parent.getId() == R.id.lv_mine_order) {
				Intent toOrderInfo;
				switch (position) {
				case 0:          //当前订单
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
			
			//其他
			if(parent.getId() == R.id.lv_mine_about) {
				switch (position) {
				case 1: 	//软件相关
					Intent toMineSoft = new Intent(mActivity, MineSoftActivity.class);
					startActivity(toMineSoft);
					break;
				case 2:		//推荐给朋友
					Intent toShare = new Intent(Intent.ACTION_SEND);
					toShare.setType("text/plain");
					toShare.putExtra(Intent.EXTRA_SUBJECT, "分享");
					toShare.putExtra(Intent.EXTRA_TEXT, "hi,推荐你使用一款软件：智慧校园客户端！");
					startActivity(Intent.createChooser(toShare, "分享到"));
					break;
				case 3:		                     
					BmobUser.logOut(mActivity);       //当前用户退出,退出到登陆界面
					Intent toLogin = new Intent(mActivity, LoginActivity.class);
					startActivity(toLogin);
					break;
				default:
					break;
				}
				
			}
			
		}
		

}
