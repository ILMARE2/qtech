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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class ShopAllActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private static final String TAG = "ShopAllActivity";

	private TextView tvTitle;
	private ListView lvShopAllList;
	private ShopListAdapter shopListAdapter;
	private TextView tv_add, tv_back;
	// ��¼��ShopActivity�д������ĵ�ǰ����������
	private String type;
	private List<Shop> shopList = new ArrayList<Shop>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_all);

		// �õ����ϼ�Activity�д����Type����
		type = getIntent().getStringExtra("type");
		// ��ȡ�̵�����
		getShopsDate();
		// ��ʼ������
		initView();

	}

	public void initView() {
		// ���ñ���
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText(getIntent().getStringExtra("title"));

		// ��ʼ��listView�����õ������
		lvShopAllList = (ListView) findViewById(R.id.lv_shop_all);
		lvShopAllList.setOnItemClickListener(this);

		// ��ʼ����Ӱ�ť
		tv_add = (TextView) this.findViewById(R.id.tv_edit);
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setText("��ӵ���");

		// ��ʼ�����ذ�ť
		tv_back = (TextView) findViewById(R.id.tv_back);

		// ���ü���
		tv_back.setOnClickListener(this);
		tv_add.setOnClickListener(this);

		// ֪ͨAdapter���ݸ���
		shopListAdapter = new ShopListAdapter(ShopAllActivity.this,
				(ArrayList<Shop>) shopList, type);
		lvShopAllList.setAdapter(shopListAdapter);
	};

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// ����ǰ�����Shop��Ϣ���ݸ���һ��Activity
		Intent toShopItem = new Intent(ShopAllActivity.this,
				ShopItemActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("shop", shopList.get(position) );
		bundle.putString("shopID", shopList.get(position).getObjectId()); // ���̵�ID��Ҫ��������,�����ȡ������null
		bundle.putString("shopName", shopList.get(position).getName());
		bundle.putString("shopLocation", shopList.get(position).getLocation());
		bundle.putString("shopInfo", shopList.get(position).getInfo());
		bundle.putString("shopPhone", shopList.get(position).getPhone());
		bundle.putString("shopSale", shopList.get(position).getSale());
		bundle.putString("shopType", shopList.get(position).getType());

		Log.i(TAG, ">>����>>" + "shopID: " + shopList.get(position).getObjectId()
				+ " shopName: " + shopList.get(position).getName());
		toShopItem.putExtras(bundle);
		startActivity(toShopItem);
	}

	/**
	 * ���ص�ǰ��������е��̵�ListView��
	 */
	private void getShopsDate() {
		BmobQuery<Shop> query = new BmobQuery<Shop>();
		query.order("-updatedAt");
		Shop shop = new Shop();
		shop.setType(type);
		query.addWhereEqualTo("type", shop.getType()); // �����Ͳ��ҡ���ѯ��ǰ���͵����е���
		query.findObjects(this, new FindListener<Shop>() {
			public void onSuccess(List<Shop> object) {
				if (object.size() == 0) {
					toast("��û����, ���ĵȴ���");
				}
				shopList = object;
				// ֪ͨAdapter���ݸ���
				shopListAdapter.refresh((ArrayList<Shop>) shopList);
				shopListAdapter.notifyDataSetChanged();
			}

			// ��ѯʧ��
			public void onError(int arg0, String msg) {
				toast("��ѯʧ��:" + msg);
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
				Toast.makeText(getApplicationContext(), "��Ǹ����û��Ȩ�ޣ�", 1).show();
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
			// ��ȡ�̵�����
			getShopsDate();
			initView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class ShopListAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mInflater = null;
		private ArrayList<Shop> mShopList = null; // ��ѡ�����µ����е����б�
		private String mType; // �̵�ķ���
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

		// ˢ���б��е�����
		public void refresh(ArrayList<Shop> list) {
			mShopList = list;
			// �����ֵ����ͱ��ת��Ϊ����
			exchangeType(mType);

			notifyDataSetChanged();
		}

		/**
		 * ���ݵ�ǰ��type����, ת������Ӧ������
		 */
		private void exchangeType(String typeString) {

			int type = Integer.parseInt(typeString);
			int fatherType = type / 10; // �����ͱ��
			int sonType = type % 10; // �����ͱ��

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

		// ����View����
		public View getView(int position, View convertView, ViewGroup parent) {
			final ShopHolder shopHodler;

			// ��ʼ���ؼ�
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

			// ��������
			Shop shop = mShopList.get(position);
			shopHodler.tvShopName.setText(mShopList.get(position).getName());
			// �̵��������Ҫ��������
			shopHodler.tvShopType.setText(mShopList.get(position).getType());
			shopHodler.tvShopLoc.setText(mShopList.get(position).getLocation());

			// ����ͼƬ����
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
