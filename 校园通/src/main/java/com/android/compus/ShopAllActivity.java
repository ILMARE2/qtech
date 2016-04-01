package com.android.compus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.bean.Shop;
import com.android.compus.data.TypeDef;
import com.android.compus.utils.NetUtil;
import com.lidroid.xutils.BitmapUtils;


/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class ShopAllActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private static final String TAG = "ShopAllActivity";

	private TextView tvTitle;
	private ListView lvShopAllList;
	private ShopListAdapter shopListAdapter;
	private TextView tv_add, tv_back;
	// 记录从ShopActivity中传过来的当前点击项的类型
	private String type;
	private List<Shop> shopList = new ArrayList<Shop>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_all);

		// 得到从上级Activity中传入的Type数据
		type = getIntent().getStringExtra("type");
		// 获取商店数据
		getShopsDate();
		// 初始化数据
		initView();

	}

	public void initView() {
		// 设置标题
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(getIntent().getStringExtra("title"));

		// 初始化listView并设置点击监听
		lvShopAllList = (ListView) findViewById(R.id.lv_shop_all);
		lvShopAllList.setOnItemClickListener(this);

		// 初始化添加按钮
		tv_add = (TextView) this.findViewById(R.id.tv_edit);
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setText("添加店铺");

		// 初始化返回按钮
		tv_back = (TextView) findViewById(R.id.tv_back);

		// 设置监听
		tv_back.setOnClickListener(this);
		tv_add.setOnClickListener(this);

		// 通知Adapter数据更新
		shopListAdapter = new ShopListAdapter(ShopAllActivity.this,
				(ArrayList<Shop>) shopList, type);
		lvShopAllList.setAdapter(shopListAdapter);
	};

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 将当前点击的Shop信息传递给下一个Activity
		Intent toShopItem = new Intent(ShopAllActivity.this,
				ShopItemActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("shop", shopList.get(position) );
		bundle.putString("shopID", shopList.get(position).getObjectId()); // 商铺的ID需要单独传递,否则获取到的是null
		bundle.putString("shopName", shopList.get(position).getName());
		bundle.putString("shopLocation", shopList.get(position).getLocation());
		bundle.putString("shopInfo", shopList.get(position).getInfo());
		bundle.putString("shopPhone", shopList.get(position).getPhone());
		bundle.putString("shopSale", shopList.get(position).getSale());
		bundle.putString("shopType", shopList.get(position).getType());

		Log.i(TAG, ">>发出>>" + "shopID: " + shopList.get(position).getObjectId()
				+ " shopName: " + shopList.get(position).getName());
		toShopItem.putExtras(bundle);
		startActivity(toShopItem);
	}

	/**
	 * 加载当前分类的所有店铺到ListView中
	 */
	private void getShopsDate() {
		BmobQuery<Shop> query = new BmobQuery<Shop>();
		query.order("-updatedAt");
		Shop shop = new Shop();
		shop.setType(type);
		query.addWhereEqualTo("type", shop.getType()); // 按类型查找。查询当前类型的所有店铺
		query.findObjects(this, new FindListener<Shop>() {
			public void onSuccess(List<Shop> object) {
				if (object.size() == 0) {
					toast("还没开张, 耐心等待吧");
				}
				shopList = object;
				// 通知Adapter数据更新
				shopListAdapter.refresh((ArrayList<Shop>) shopList);
				shopListAdapter.notifyDataSetChanged();
			}

			// 查询失败
			public void onError(int arg0, String msg) {
				toast("查询失败:" + msg);
			}
		});
	}

	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_edit:
			if ("admin".equals(BmobUser.getCurrentUser(getApplicationContext())
					.getUsername())) {
				Intent intent = new Intent(getApplicationContext(),
						AddShopActivity.class);
				intent.putExtra("type", type);
				startActivityForResult(intent, 0);
			} else {
				Toast.makeText(getApplicationContext(), "抱歉，您没有权限！", 1).show();
			}

			break;
		case R.id.tv_back:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			// 获取商店数据
			getShopsDate();
			initView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class ShopListAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mInflater = null;
		private ArrayList<Shop> mShopList = null; // 所选分类下的所有店铺列表
		private String mType; // 商店的分类
		private BitmapUtils bitmapUtils;

		public ShopListAdapter(Context context, ArrayList<Shop> shopList,
				String type) {
			mContext = context;
			mShopList = shopList;
			mType = type;
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mShopList.size();
		}

		@Override
		public Object getItem(int position) {
			return mShopList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 刷新列表中的数据
		public void refresh(ArrayList<Shop> list) {
			mShopList = list;
			// 将数字的类型编号转换为文字
			exchangeType(mType);

			notifyDataSetChanged();
		}

		/**
		 * 根据当前的type类型, 转换成相应的文字
		 */
		private void exchangeType(String typeString) {

			int type = Integer.parseInt(typeString);
			int fatherType = type / 10; // 父类型编号
			int sonType = type % 10; // 子类型编号

			Iterator<Shop> iterator = mShopList.iterator();
			while (iterator.hasNext()) {
				switch (fatherType) {
				case 2:
					iterator.next().setType(
							TypeDef.typeDadList[fatherType - 1] + "/"
									+ TypeDef.typeSonList2[sonType - 1]);
					break;
				case 3:
					iterator.next().setType(
							TypeDef.typeDadList[fatherType - 1] + "/"
									+ TypeDef.typeSonList3[sonType - 1]);
					break;

				default:
					break;
				}

			}
		}

		// 返回View对象
		public View getView(int position, View convertView, ViewGroup parent) {
			final ShopHolder shopHodler;

			// 初始化控件
			View view = mInflater.inflate(R.layout.shop_all_list_item, null);
			shopHodler = new ShopHolder();
			shopHodler.tvShopName = (TextView) view
					.findViewById(R.id.tv_shop_name);
			shopHodler.tvShopType = (TextView) view
					.findViewById(R.id.tv_shop_type);
			shopHodler.tvShopLoc = (TextView) view
					.findViewById(R.id.tv_shop_loc);
			shopHodler.img_shop = (ImageView) view.findViewById(R.id.img_shop);
			view.setTag(shopHodler);

			// 设置数据
			Shop shop = mShopList.get(position);
			shopHodler.tvShopName.setText(mShopList.get(position).getName());
			// 商店的类型需要单独处理
			shopHodler.tvShopType.setText(mShopList.get(position).getType());
			shopHodler.tvShopLoc.setText(mShopList.get(position).getLocation());

			// 设置图片数据
			final BmobFile bmobFile = shop.getPicShop();

			if (bmobFile != null) {
				bitmapUtils = new BitmapUtils(getApplicationContext());
				bitmapUtils.display(shopHodler.img_shop, bmobFile.getFileUrl());
			}

			// if (bmobFile != null) {
			// new AsyncTask<Void, Void, Bitmap>() {
			// protected void onPreExecute() {
			// super.onPreExecute();
			// }
			//
			// protected void onPostExecute(Bitmap result) {
			// if (result != null) {
			// shopHodler.img_shop.setImageBitmap(result);
			// }
			// super.onPostExecute(result);
			// }
			//
			// @Override
			// protected Bitmap doInBackground(Void... params) {
			// try {
			// Bitmap bitmap = NetUtil.getImage(bmobFile
			// .getFileUrl());
			// return bitmap;
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// return null;
			// }
			//
			// }
			//
			// }.execute();
			// } else {
			// }

			return view;
		}
	}

	public class ShopHolder {
		public TextView tvShopName;
		public TextView tvShopType;
		public TextView tvShopLoc;
		public ImageView img_shop;

	}

}
