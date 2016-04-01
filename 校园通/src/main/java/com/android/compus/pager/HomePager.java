package com.android.compus.pager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.compus.DomActivity;
import com.android.compus.R;
import com.android.compus.ShopAllActivity;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ��������ҳ
 * ===============================
 */
public class HomePager extends BasePager implements OnItemClickListener {

	private ListView home_lv; 				//У԰���� 
	
	private ViewPager viewPager;    		//У԰�չʾ
	
	private LinearLayout pointGroup;		//ָʾ��
	
	private TextView iamgeDesc;             //ͼƬ��������

	// �ֲ�ͼƬ��ԴID
	private final int[] imageIds = { R.drawable.roll, R.drawable.roll2, R.drawable.compus,
			R.drawable.roll4 };

	// ͼƬ���⼯��
	private final String[] imageDescriptions = { "У԰--��ѧ¥",
			"У԰һ��--�ٳ�", "У԰�", "У԰һ��--��ѧ¥"};
	
	//У԰����
	public  String[] school_service = {"���ᱨ��","��ӡ",  "���г�", "���յ���", "���ᳬ��", "һ��ʳ��", "����ʳ��", "��ɽ���ܱ�"};
	
	private ArrayList<ImageView> imageList=null;

	//��¼��ǰѡ�е�����
	protected int lastPosition;

	//�ж��Ƿ��Զ�����
	public boolean isRunning = false;
	
	//ListView��Ӧ��item ��Ŀ��ͼ��
	private int[] mGiftImages = { R.drawable.ic_7, R.drawable.ic_7,
			 R.drawable.ic_7, R.drawable.ic_7, R.drawable.ic_7,
			R.drawable.ic_4, R.drawable.ic_4, R.drawable.ic_4 };

	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// ��viewPager ��������һҳ
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		};
	};

	//������
	private MyPagerAdapter myadapter;
	
	public HomePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initSuccessView() {
		//�Ƴ������е�view
		fl_content.removeAllViews();
		isRunning=false;   //ͣ��֮ǰ���ֲ�
		tv_title.setText("�ǻ�У԰");

		//��ʼ��Ҫ��ʾ��View
		View view = View.inflate(mActivity, R.layout.activity_home, null);
		home_lv =  (ListView) view.findViewById(R.id.home_lv);
		viewPager = (ViewPager)view.findViewById(R.id.viewpager);
		pointGroup = (LinearLayout) view.findViewById(R.id.point_group);
		iamgeDesc = (TextView) view.findViewById(R.id.image_desc);
		
	
		//ͼƬ�ֲ�
		iamgeDesc.setText(imageDescriptions[0]);
		
		initViewPagerData();
		
		myadapter = new MyPagerAdapter();
		viewPager.setAdapter(myadapter);
		
		home_lv.setAdapter(new Myadapter());
		
		home_lv.setOnItemClickListener(this);
		
		viewPager.setCurrentItem(Integer.MAX_VALUE/2 -(Integer.MAX_VALUE/2%imageList.size())) ;
		 
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * ҳ���л������ 
			 * position  �µ�ҳ��λ��
			 */
			public void onPageSelected(int position) {
				position = position % imageList.size();
				// ����������������
				iamgeDesc.setText(imageDescriptions[position]);
				// �ı�ָʾ���״̬
				// �ѵ�ǰ��enbale Ϊtrue
				pointGroup.getChildAt(position).setEnabled(true);
				// ����һ������Ϊfalse
				pointGroup.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;

			}
			
			// ҳ�����ڻ�����ʱ�򣬻ص�
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			//��ҳ��״̬�����仯��ʱ�򣬻ص�
			public void onPageScrollStateChanged(int state) {
			}
		});

		/*
		 * �Զ�ѭ���� 1����ʱ����Timer 2�������߳� while true ѭ�� 3��ColckManager 4�� ��handler
		 * ������ʱ��Ϣ��ʵ��ѭ��
		 */
		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 3000);

		// ��FrameLayout�ж�̬���view
		fl_content.addView(view);
		
	}

	private void initViewPagerData() {
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			// ��ʼ��ͼƬ��Դ
			ImageView image = new ImageView(mActivity);
			image.setBackgroundResource(imageIds[i]);
			imageList.add(image);

			// ���ָʾ��
			ImageView point = new ImageView(mActivity);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.rightMargin = 20;
			point.setLayoutParams(params);

			point.setBackgroundResource(R.drawable.point_bg);
			if (i == 0) {
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}
			pointGroup.addView(point);
		}

	}

	private void toast(String toast) {
		Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
	};
	
	private class MyPagerAdapter extends PagerAdapter {

		/**
		 * ���ҳ�������
		 */
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		/**
		 * �����Ӧλ���ϵ�view
		 * container  view����������ʵ����viewpager����
		 * position 	��Ӧ��λ��
		 */
		public Object instantiateItem(ViewGroup container, int position) {
			// �� container ���һ��view
			container.addView(imageList.get(position % imageList.size()));
			// ����һ���͸�view��Ե�object
			return imageList.get(position % imageList.size());
		}


		//�ж� view��object�Ķ�Ӧ��ϵ 
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		
		 //���ٶ�Ӧλ���ϵ�object
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageList.get(position % imageList.size()));
		}
	}
	
	
	private class Myadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mGiftImages.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view =View.inflate(mActivity, R.layout.home_item, null);
			ViewHodler hodler=new ViewHodler();
			hodler.home_item_body=(TextView) view.findViewById(R.id.home_item_body);
			hodler.home_item_icon = (ImageView) view.findViewById(R.id.home_item_icon);
			hodler.home_item_body.setText(school_service[position]);
			hodler.home_item_icon.setImageResource(mGiftImages[position]);
			view.setTag(hodler);
			return view;
					
		}
		
	}
	
	
	
	static class ViewHodler {
		private ImageView home_item_icon;
		private TextView home_item_body;
	}
	
	/**
	 * @param title
	 *            ���������
	 * @param type
	 */
	private void toShopAllActivity(String title, String type) {
		Intent toShopAll = new Intent(mActivity, ShopAllActivity.class);
		toShopAll.putExtra("title", title);
		// ��ǰ�������ĸ�����
		toShopAll.putExtra("type", type);
		mActivity.startActivity(toShopAll);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println(position);
		switch (parent.getId()) {
		case R.id.home_lv:
			if (position == 0) {
				Intent toDOMActivity = new Intent(mActivity,
						DomActivity.class);
				mActivity.startActivity(toDOMActivity);
			}else if(position == 5){
				toShopAllActivity(school_service[position], "21");
						
			}else if(position == 6){
				toShopAllActivity(school_service[position], "22");
			}else if(position == 7){
				toShopAllActivity(school_service[position], "23");
			}else {
				toShopAllActivity(school_service[position], "3"
						+ (position + 2));
			}
			break;

		default:
			break;
		}

	}
	
	
	
}
