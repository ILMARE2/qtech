package com.android.compus;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.android.compus.bean.Order;
import com.android.compus.view.OrderInfoListAdapter;

/**
 * 订单详情页面
 * 
 */
public class OrderInfoActivity extends BaseActivity implements
		OnItemLongClickListener {

	private ListView lvOrderInfo;    //用于显示的ListView
	
	private OrderInfoListAdapter orderInfoListAdapter;  //ListView的适配器
	
	private List<Order> orderList = new ArrayList<Order>();  //listView数据的集合
	
	private TextView tv_title,tv_back;    //返回按钮和标题
	
	private String type = "";       // now-当前订单 old-历史订单


	/**
	 * 初始化各种空间
	 */
	protected void initView() {
		setContentView(R.layout.activity_order_info);
		
		//设置标题
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("订单详情");
		
		//返回按钮点击事件
		tv_back=(TextView) findViewById(R.id.tv_back);
		
		//初始化ListView 
		lvOrderInfo = (ListView) findViewById(R.id.lv_order_info);
		orderInfoListAdapter = new OrderInfoListAdapter(this, orderList);
		lvOrderInfo.setAdapter(orderInfoListAdapter);
		
		super.initView();
	}
	
	/**
	 * 设置各种控件的Listener
	 */
	protected void setListener() {
		
		//返回按按钮的点击事件
		tv_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		//订单列表设置条目点击事件
		lvOrderInfo.setOnItemLongClickListener(this);
		super.setListener();
	}


	/**
	 *  初始化列表菜单中数据
	 */
	public void initData() {
		type = getIntent().getStringExtra("type");
		
		// 获取用户
		BmobUser user = BmobUser.getCurrentUser(this);

		// 获取小菜订单(数量)
		BmobQuery<Order> query = new BmobQuery<Order>();
		//按创建顺序
		query.order("-updatedAt");
		//确定查询条件  userName 和  订单类型
		query.addWhereEqualTo("userName", user.getUsername());
		if (type.equals("now")) {
			query.addWhereEqualTo("state", "未取餐");
		} else if (type.equals("old")) {
			query.addWhereEqualTo("state", "已取餐");
		} 
		
		//开始查询
		query.findObjects(this, new FindListener<Order>() {
			public void onSuccess(List<Order> object) {
				if (object.size() == 0)
					toast("亲, 您还木有订单哦");
				orderList = object;
				// 通知Adapter数据更新
				orderInfoListAdapter.refresh(orderList);
				orderInfoListAdapter.notifyDataSetChanged();
			}

			public void onError(int arg0, String arg1) {
				toast("查询失败");
			}
		});

	}

	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	// 订单长按响应事件
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final Order order = orderList.get(position);
		
		PopupMenu popup = new PopupMenu(this, lvOrderInfo);
		popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
	
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.modify:               // 修改
					order.setState("已取餐");
					order.update(getApplicationContext(),order.getObjectId() , new UpdateListener() {
						public void onSuccess() {
							toast("确认收货成功！");
							initData();
							initView();
						}
						public void onFailure(int arg0, String arg1) {
							toast("确认收货失败！");
						}
					});
					break;
				case R.id.cancel:                // 取消订单
					order.delete(getApplicationContext(), new DeleteListener() {
						public void onSuccess() {
							toast("删除订单成功！");
							initData();
							initView();
						}
						public void onFailure(int arg0, String arg1) {
							toast("删除订单失败！");
						}
					});
					break;
				default:
					break;
				}
				return true;
			}
		});
		
		popup.show();
		return false;
	};

}
