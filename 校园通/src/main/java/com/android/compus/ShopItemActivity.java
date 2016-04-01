package com.android.compus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.bean.Good;
import com.android.compus.view.GoodsListAdapter;
import com.android.compus.view.ViewPagerAdapter;
import com.android.compus.view.ViewPagerCompat;


/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class ShopItemActivity extends Activity implements OnClickListener, OnItemClickListener{

	private static final String TAG = "ShopItemActivity";

	// ViewPagerҳ
	private View view1, view2;                      // ��Ҫ������ҳ��
	private ViewPagerCompat viewPager;              // viewpager
	private ViewPagerAdapter shopViewPagerAdapter;
	private PagerTitleStrip pagerTitleStrip;        // viewpager�ı���
	private PagerTabStrip pagerTabStrip;            // һ��viewpager��ָʾ����Ч������һ����Ĵֵ��»���
	private List<View> viewList;                    // ����Ҫ������ҳ����ӵ����list��
	private List<String> titleList;                 // viewpager�ı���
	
	private ListView lvGoodsList;                   // ������Ʒ�б�
	private GoodsListAdapter goodsListAdapter;
	private Button btnBuyGood;

	// ���̼��ҳ�еĿؼ�
	private TextView tvShopName;                    // ������
	private TextView tvShopInfo;                    // ���̼��
	private TextView tvShopSale;                    // ���̴�����Ϣ
	private TextView tvShopLoc;                     // ���̵���λ��
	private TextView tvShopPhone;                   // ���̵绰
	
	private String ShopName;
	private String ShopInfo;
	private String ShopSale;
	private String ShopLoc;
	private String ShopPhone;
	private String ShopType;
	private String shopID;                           // ��ǰѡ���Shop��ID
	
	private Button btnCommit;
	private EditText etCommit;
	private LinearLayout llCommitParent;              // ���۸����Բ���
	private LinearLayout llCommitSon;                 // ���������Բ���
	private ImageView imgCall;                        // ����绰

	// UI��������
	private static List<Good> goodsList;

	// ���ϼ�ҳ���д��������
	private Good selectGood;
	
    private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_item);

		// ��ȡ����ShopAllActivity�д��ݹ�����Shop����	
		 bundle=getIntent().getExtras();
		 ShopName= getIntent().getStringExtra("shopName");
		 ShopInfo= getIntent().getStringExtra("shopInfo");
		 ShopSale= getIntent().getStringExtra("shopSale");
		 ShopLoc= getIntent().getStringExtra("shopLocation");
		 ShopPhone= getIntent().getStringExtra("shopPhone");
		 ShopType=getIntent().getStringExtra("shopType");
		 shopID = getIntent().getStringExtra("shopID");
		

		
		// ��ʼ����Ʒҳ���Լ���������
		initGoodsDate();
		initView();
		
		
	}

	public void initView() {

		viewPager = (ViewPagerCompat) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(Color.argb(255, 255, 127, 39));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(50);
		pagerTabStrip.setTextColor(Color.argb(255, 255, 127, 39));

		view1 = LayoutInflater.from(this)
				.inflate(R.layout.viewpager_menu, null);
		
		view2 = LayoutInflater.from(this).inflate(R.layout.viewpager_shopinfo,
				null);
		
		initContentView();

		viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������
		viewList.add(view1);
		viewList.add(view2);

		titleList = new ArrayList<String>();// ÿ��ҳ���Title����
		titleList.add("��Ʒ");
		titleList.add("���̼��");
		shopViewPagerAdapter = new ViewPagerAdapter(viewList, titleList);

		viewPager.setAdapter(shopViewPagerAdapter);
		
		viewPager.setCurrentItem(0);

	}

	/**
	 * ��ȡĳһ�̵��������Ʒ
	 * 
	 */
	public void initGoodsDate() {
		goodsList = new ArrayList<Good>();
		goodsListAdapter = new GoodsListAdapter(this, goodsList);
		BmobQuery<Good> query = new BmobQuery<Good>();
		query.addWhereEqualTo("shopID", shopID);
		// �ȴӻ���ȡ���ݣ����û�У��ٴ�����ȡ��
		query.setLimit(15); // �������15�����
		query.findObjects(this, new FindListener<Good>() {

			@Override
			public void onSuccess(List<Good> goods) {
				// toast("��ѯ��Ʒ�ɹ�, ��" + goods.size());
				if (goods.size() == 0) {
					toast("��, �õ껹û�������ƷŶ");
				}
				goodsList = goods;
				goodsListAdapter.refresh(goodsList);
				goodsListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				toast("��ѯʧ��");
			}
		});

	}

	public void initContentView() {
		// ��Ʒ�б�ҳ
		lvGoodsList = (ListView) view1.findViewById(R.id.lv_goods_list);
		lvGoodsList.setAdapter(goodsListAdapter);
		lvGoodsList.setOnItemClickListener(this);

		
		// ���̼��ҳ
		tvShopName = (TextView) view2.findViewById(R.id.tv_shop_title);
		tvShopInfo = (TextView) view2.findViewById(R.id.tv_shop_introduce);
		tvShopSale = (TextView) view2.findViewById(R.id.tv_shop_promotion);
		tvShopLoc = (TextView) view2.findViewById(R.id.tv_shop_location);
		tvShopPhone = (TextView) view2.findViewById(R.id.tv_shop_phone);
		tvShopName.setText(ShopName);                                  // ���õ�����
		tvShopInfo.setText(ShopInfo);                                  // ���õ��̼��
		tvShopSale.setText(ShopSale);                                  // ���õ��̹���
		tvShopLoc.setText("λ�ã�" + ShopLoc);                          // ���õ���λ��
		tvShopPhone.setText("�绰��" + ShopPhone);                      // ���õ�����ϵ�绰

		btnCommit = (Button) view2.findViewById(R.id.btn_commit);
		btnCommit.setOnClickListener(this);

		// ��ȡ�����۵Ĳ���
		etCommit = (EditText) view2.findViewById(R.id.et_commit);
		llCommitParent = (LinearLayout) view2
				.findViewById(R.id.ll_commit_parent_view);
		llCommitSon = (LinearLayout) findViewById(R.id.ll_commit_son_view);

		imgCall = (ImageView) view2.findViewById(R.id.img_call);
		imgCall.setOnClickListener(this);

	}

	/**
	 * ���һ������
	 */
	public void insertCommit(String user, String content) {
		View view = LayoutInflater.from(this).inflate(R.layout.commit, null);
		TextView tvUser = (TextView) view.findViewById(R.id.tv_commit_user);
		TextView tvContent = (TextView) view
				.findViewById(R.id.tv_commit_content);
		tvUser.setText(user);
		tvContent.setText(content);
		llCommitParent.addView(view);
		tvUser = null;
		tvContent = null;
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit:
			if (etCommit.getText().toString().equals("")) {
				toast("�ף���дһ���");
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy��MM��dd��  HH:mm:ss ");
				Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
				String time = formatter.format(curDate);
				String content = etCommit.getText().toString() + " [ " + time
						+ " ] ";
				insertCommit("admin" + ":", content);
				etCommit.setText("");
			}
			break;

		case R.id.img_call:
			
			break;
		
		default:
			break;
		}

	}


	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (viewPager.getCurrentItem()) {
		case 0:

			selectGood =  goodsList.get(position);
			Intent toOrderActivity = new Intent(ShopItemActivity.this, OrderActivity.class);  
			bundle.putSerializable("good", selectGood);
	        toOrderActivity.putExtras(bundle);
			startActivity(toOrderActivity);

			break;
		case 1:
			break;
		default:
			break;
		}
		
	}
	
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

}
