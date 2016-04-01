package com.android.compus;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.InsertListener;

import com.android.compus.bean.Good;
import com.android.compus.bean.Order;
import com.android.compus.pay.ShoppingCartActivity;
import com.android.compus.utils.Util;

/**
 * Ӧ��������
 * @date  2014-5-13
 * @author Stone
 */
public class OrderActivity extends Activity implements OnClickListener{
	
	private TextView tvOrderShop; 		// ����
	private TextView tvOrderGood; 		// ����
	private TextView tvOrderCount; 		// ����
	private TextView tvOrderTime;		// ȡ��ʱ��
	private TimePicker tpOrderTime;  	// ʱ��ѡ��ؼ�
	private EditText etOrderPhone;		// ��ϵ�绰
	private EditText etOrderWords; 		// ��������
	private Button btnOrderCountMore; 	// ��������
	private Button btnOrderCountLess; 	// ��������

	private Button btnOrderSetTime;		// ����ʱ��
	private Button btnDlgOk;			// ����ʱ�����
	private Button btnOrderSubmit; 		// �ύ����

	private int mHour;
    private int mMinute;
    private String time = "12 : 30";
    private TimePickerDialog dlgSetOrderTime;
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };
	
	// ���ϼ�ҳ���д��������
	//private Shop shop;
    // ����ѡ���Shop
    private  Bundle bundle;
	private Good good; //��ǰѡ�����Ʒ
	private String shopID; // ��ǰѡ���Shop��ID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		//shop = (Shop) getIntent().getSerializableExtra("shop");
		bundle=getIntent().getExtras();
		good = (Good) getIntent().getSerializableExtra("good");
		shopID = getIntent().getStringExtra("shopID");
		
		initView();
		//initDlgView();
	}
	
	private void initView() {
		
		tvOrderShop = (TextView) findViewById(R.id.tv_order_shop);
		tvOrderGood = (TextView) findViewById(R.id.tv_order_good);
		tvOrderCount = (TextView) findViewById(R.id.tv_order_count);
		tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
//		tvOrderShop.setText(shop.getName());
		tvOrderShop.setText(bundle.getString("shopName", ""));
		tvOrderGood.setText(good.getName());
		
		
		etOrderPhone = (EditText) findViewById(R.id.et_order_phone);
		etOrderWords = (EditText) findViewById(R.id.et_order_words);
		
		btnOrderCountMore = (Button)  findViewById(R.id.btn_order_count_more);
		btnOrderCountLess = (Button)  findViewById(R.id.btn_order_count_less);
		btnOrderSetTime = (Button) findViewById(R.id.btn_set_time);
		btnOrderSubmit = (Button) findViewById(R.id.btn_order_submit);
		btnOrderCountMore.setOnClickListener(this);
		btnOrderCountLess.setOnClickListener(this);
		btnOrderSetTime.setOnClickListener(this);
		btnOrderSubmit.setOnClickListener(this);
		
	}
	
//	private void initDlgView() {
//		LayoutInflater inflater = LayoutInflater.from(this);
//		dlgOrderView = inflater.inflate(R.layout.dlg_order_settime, null);
//		tpOrderTime = (TimePicker) dlgOrderView.findViewById(R.id.tp_dlg_time);
//		tpOrderTime.setIs24HourView(true);
//		btnDlgOk = (Button) dlgOrderView.findViewById(R.id.btn_dlg_ok);
//		btnDlgOk.setOnClickListener(this);
//	}

	@Override
	public void onClick(View v) {
		int count = 1;
		switch (v.getId()) {
		case R.id.btn_order_count_more:
			count = Integer.parseInt(tvOrderCount.getText().toString());
				tvOrderCount.setText( (count+1)+"");
			break;
		case R.id.btn_order_count_less:
			count = Integer.parseInt(tvOrderCount.getText().toString());
			if (count == 1) {
				toast("�ף� ÿ�ݶ�����������Ϊ 1 Ŷ");
			} else {
				tvOrderCount.setText( (count-1)+"");
			}
			break;
		case R.id.btn_set_time:
//			dlgSetOrderTime = new DialogOrder(this, R.style.MyDialog);
//			dlgSetOrderTime.show();
			final Calendar c = Calendar.getInstance();
	        mHour = c.get(Calendar.HOUR_OF_DAY);
	        mMinute = c.get(Calendar.MINUTE);
			dlgSetOrderTime = new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
			dlgSetOrderTime.show();
			break;
		case R.id.btn_dlg_ok:
			time = tpOrderTime.getCurrentHour()+ " : " + tpOrderTime.getCurrentMinute();
			dlgSetOrderTime.dismiss();
			
		case R.id.btn_order_submit:
			
			if(postOrder()){
				finish();
				
			}
			
			break;
		default:
			break;
		}
	
	}

	/**
	 * �ύ��������
	 */
	private Boolean postOrder() {
		String count = tvOrderCount.getText().toString();
		String phone = etOrderPhone.getText().toString();
		String words = etOrderWords.getText().toString();
		float price = Integer.parseInt(count)* Float.parseFloat(good.getPrice());
		
		
		if( !Util.isPhoneNumberValid(phone) ) {
			toast("��������ȷ����ϵ�绰, ����ȡ��");
			return false;
		} else {
			final Order order = new Order();
			BmobUser user = BmobUser.getCurrentUser(this);
			order.setUserName(user.getUsername());
			order.setGoodID(good.getObjectId());
			order.setGoodName(good.getName());

			order.setShopID(bundle.getString("shopID",""));
			order.setShopName(bundle.getString("shopName",""));
			order.setCount(count);
			order.setTime(time);
			order.setPrice(price+"");
			order.setPhone(phone);
			order.setTips(words);
			order.insertObject(this, new InsertListener() {
				
				@Override
				public void onSuccess() {
					toast("�����ύ�ɹ�");
					
					
					Bundle bundle=new Bundle();
					bundle.putSerializable("order", order);
					
					Intent intent=new Intent(OrderActivity.this,ShoppingCartActivity.class);
					intent.putExtra("order", bundle);
					startActivity(intent);
				}
				
				@Override
				public void onFailure(String arg0) {
					toast("�����ύʧ��");
				}
			});
			return true;
			
		}
	}
	
	private void updateDisplay() {
		time = mHour + " : " + mMinute;
		tvOrderTime.setText(time);
	}
	
	private void back() {
		Intent back = new Intent(OrderActivity.this, ShopItemActivity.class);
        bundle.putString("shopID", shopID);   //���̵�ID��Ҫ��������,�����ȡ������null
        back.putExtras(bundle);
		startActivity(back);
	}
	
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	
}
