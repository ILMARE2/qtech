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
 * ��������ҳ��
 * 
 */
public class OrderInfoActivity extends BaseActivity implements
		OnItemLongClickListener {

	private ListView lvOrderInfo;    //������ʾ��ListView
	
	private OrderInfoListAdapter orderInfoListAdapter;  //ListView��������
	
	private List<Order> orderList = new ArrayList<Order>();  //listView���ݵļ���
	
	private TextView tv_title,tv_back;    //���ذ�ť�ͱ���
	
	private String type = "";       // now-��ǰ���� old-��ʷ����


	/**
	 * ��ʼ�����ֿռ�
	 */
	protected void initView() {
		setContentView(R.layout.activity_order_info);
		
		//���ñ���
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("��������");
		
		//���ذ�ť����¼�
		tv_back=(TextView) findViewById(R.id.tv_back);
		
		//��ʼ��ListView 
		lvOrderInfo = (ListView) findViewById(R.id.lv_order_info);
		orderInfoListAdapter = new OrderInfoListAdapter(this, orderList);
		lvOrderInfo.setAdapter(orderInfoListAdapter);
		
		super.initView();
	}
	
	/**
	 * ���ø��ֿؼ���Listener
	 */
	protected void setListener() {
		
		//���ذ���ť�ĵ���¼�
		tv_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		//�����б�������Ŀ����¼�
		lvOrderInfo.setOnItemLongClickListener(this);
		super.setListener();
	}


	/**
	 *  ��ʼ���б�˵�������
	 */
	public void initData() {
		type = getIntent().getStringExtra("type");
		
		// ��ȡ�û�
		BmobUser user = BmobUser.getCurrentUser(this);

		// ��ȡС�˶���(����)
		BmobQuery<Order> query = new BmobQuery<Order>();
		//������˳��
		query.order("-updatedAt");
		//ȷ����ѯ����  userName ��  ��������
		query.addWhereEqualTo("userName", user.getUsername());
		if (type.equals("now")) {
			query.addWhereEqualTo("state", "δȡ��");
		} else if (type.equals("old")) {
			query.addWhereEqualTo("state", "��ȡ��");
		} 
		
		//��ʼ��ѯ
		query.findObjects(this, new FindListener<Order>() {
			public void onSuccess(List<Order> object) {
				if (object.size() == 0)
					toast("��, ����ľ�ж���Ŷ");
				orderList = object;
				// ֪ͨAdapter���ݸ���
				orderInfoListAdapter.refresh(orderList);
				orderInfoListAdapter.notifyDataSetChanged();
			}

			public void onError(int arg0, String arg1) {
				toast("��ѯʧ��");
			}
		});

	}

	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	// ����������Ӧ�¼�
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final Order order = orderList.get(position);
		
		PopupMenu popup = new PopupMenu(this, lvOrderInfo);
		popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
	
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.modify:               // �޸�
					order.setState("��ȡ��");
					order.update(getApplicationContext(),order.getObjectId() , new UpdateListener() {
						public void onSuccess() {
							toast("ȷ���ջ��ɹ���");
							initData();
							initView();
						}
						public void onFailure(int arg0, String arg1) {
							toast("ȷ���ջ�ʧ�ܣ�");
						}
					});
					break;
				case R.id.cancel:                // ȡ������
					order.delete(getApplicationContext(), new DeleteListener() {
						public void onSuccess() {
							toast("ɾ�������ɹ���");
							initData();
							initView();
						}
						public void onFailure(int arg0, String arg1) {
							toast("ɾ������ʧ�ܣ�");
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
