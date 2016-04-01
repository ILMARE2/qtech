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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class ShopItemActivity extends Activity implements OnClickListener, OnItemClickListener{

	private static final String TAG = "ShopItemActivity";

	// ViewPager页
	private View view1, view2;                      // 需要滑动的页卡
	private ViewPagerCompat viewPager;              // viewpager
	private ViewPagerAdapter shopViewPagerAdapter;
	private PagerTitleStrip pagerTitleStrip;        // viewpager的标题
	private PagerTabStrip pagerTabStrip;            // 一个viewpager的指示器，效果就是一个横的粗的下划线
	private List<View> viewList;                    // 把需要滑动的页卡添加到这个list中
	private List<String> titleList;                 // viewpager的标题
	
	private ListView lvGoodsList;                   // 店铺商品列表
	private GoodsListAdapter goodsListAdapter;
	private Button btnBuyGood;

	// 店铺简介页中的控件
	private TextView tvShopName;                    // 店铺名
	private TextView tvShopInfo;                    // 店铺简介
	private TextView tvShopSale;                    // 店铺促销信息
	private TextView tvShopLoc;                     // 店铺地理位置
	private TextView tvShopPhone;                   // 店铺电话
	
	private String ShopName;
	private String ShopInfo;
	private String ShopSale;
	private String ShopLoc;
	private String ShopPhone;
	private String ShopType;
	private String shopID;                           // 当前选择的Shop的ID
	
	private Button btnCommit;
	private EditText etCommit;
	private LinearLayout llCommitParent;              // 评论父线性布局
	private LinearLayout llCommitSon;                 // 评论子线性布局
	private ImageView imgCall;                        // 拨打电话

	// UI测试数据
	private static List<Good> goodsList;

	// 从上级页面中传入的数据
	private Good selectGood;
	
    private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_item);

		// 获取到从ShopAllActivity中传递过来的Shop对象	
		 bundle=getIntent().getExtras();
		 ShopName= getIntent().getStringExtra("shopName");
		 ShopInfo= getIntent().getStringExtra("shopInfo");
		 ShopSale= getIntent().getStringExtra("shopSale");
		 ShopLoc= getIntent().getStringExtra("shopLocation");
		 ShopPhone= getIntent().getStringExtra("shopPhone");
		 ShopType=getIntent().getStringExtra("shopType");
		 shopID = getIntent().getStringExtra("shopID");
		

		
		// 初始化商品页面以及适配数据
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

		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		viewList.add(view1);
		viewList.add(view2);

		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("商品");
		titleList.add("店铺简介");
		shopViewPagerAdapter = new ViewPagerAdapter(viewList, titleList);

		viewPager.setAdapter(shopViewPagerAdapter);
		
		viewPager.setCurrentItem(0);

	}

	/**
	 * 获取某一商店的所有商品
	 * 
	 */
	public void initGoodsDate() {
		goodsList = new ArrayList<Good>();
		goodsListAdapter = new GoodsListAdapter(this, goodsList);
		BmobQuery<Good> query = new BmobQuery<Good>();
		query.addWhereEqualTo("shopID", shopID);
		// 先从缓存取数据，如果没有，再从网络取。
		query.setLimit(15); // 限制最多15个结果
		query.findObjects(this, new FindListener<Good>() {

			@Override
			public void onSuccess(List<Good> goods) {
				// toast("查询商品成功, 共" + goods.size());
				if (goods.size() == 0) {
					toast("亲, 该店还没有添加商品哦");
				}
				goodsList = goods;
				goodsListAdapter.refresh(goodsList);
				goodsListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				toast("查询失败");
			}
		});

	}

	public void initContentView() {
		// 商品列表页
		lvGoodsList = (ListView) view1.findViewById(R.id.lv_goods_list);
		lvGoodsList.setAdapter(goodsListAdapter);
		lvGoodsList.setOnItemClickListener(this);

		
		// 店铺简介页
		tvShopName = (TextView) view2.findViewById(R.id.tv_shop_title);
		tvShopInfo = (TextView) view2.findViewById(R.id.tv_shop_introduce);
		tvShopSale = (TextView) view2.findViewById(R.id.tv_shop_promotion);
		tvShopLoc = (TextView) view2.findViewById(R.id.tv_shop_location);
		tvShopPhone = (TextView) view2.findViewById(R.id.tv_shop_phone);
		tvShopName.setText(ShopName);                                  // 设置店铺名
		tvShopInfo.setText(ShopInfo);                                  // 设置店铺简介
		tvShopSale.setText(ShopSale);                                  // 设置店铺公告
		tvShopLoc.setText("位置：" + ShopLoc);                          // 设置店铺位置
		tvShopPhone.setText("电话：" + ShopPhone);                      // 设置店铺联系电话

		btnCommit = (Button) view2.findViewById(R.id.btn_commit);
		btnCommit.setOnClickListener(this);

		// 获取到评论的布局
		etCommit = (EditText) view2.findViewById(R.id.et_commit);
		llCommitParent = (LinearLayout) view2
				.findViewById(R.id.ll_commit_parent_view);
		llCommitSon = (LinearLayout) findViewById(R.id.ll_commit_son_view);

		imgCall = (ImageView) view2.findViewById(R.id.img_call);
		imgCall.setOnClickListener(this);

	}

	/**
	 * 添加一条评论
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
				toast("亲，先写一句吧");
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy年MM月dd日  HH:mm:ss ");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
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
