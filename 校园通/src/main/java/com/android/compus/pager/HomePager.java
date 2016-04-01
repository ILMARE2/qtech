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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：首页
 * ===============================
 */
public class HomePager extends BasePager implements OnItemClickListener {

	private ListView home_lv; 				//校园服务 
	
	private ViewPager viewPager;    		//校园活动展示
	
	private LinearLayout pointGroup;		//指示点
	
	private TextView iamgeDesc;             //图片描述文字

	// 轮播图片资源ID
	private final int[] imageIds = { R.drawable.roll, R.drawable.roll2, R.drawable.compus,
			R.drawable.roll4 };

	// 图片标题集合
	private final String[] imageDescriptions = { "校园--教学楼",
			"校园一角--操场", "校园活动", "校园一角--教学楼"};
	
	//校园服务
	public  String[] school_service = {"宿舍报修","打印",  "自行车", "生日蛋糕", "宿舍超市", "一区食堂", "二区食堂", "后山及周边"};
	
	private ArrayList<ImageView> imageList=null;

	//记录当前选中的数据
	protected int lastPosition;

	//判断是否自动滚动
	public boolean isRunning = false;
	
	//ListView对应的item 条目的图标
	private int[] mGiftImages = { R.drawable.ic_7, R.drawable.ic_7,
			 R.drawable.ic_7, R.drawable.ic_7, R.drawable.ic_7,
			R.drawable.ic_4, R.drawable.ic_4, R.drawable.ic_4 };

	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 让viewPager 滑动到下一页
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		};
	};

	//适配器
	private MyPagerAdapter myadapter;
	
	public HomePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initSuccessView() {
		//移除掉所有的view
		fl_content.removeAllViews();
		isRunning=false;   //停下之前的轮播
		tv_title.setText("智慧校园");

		//初始化要显示的View
		View view = View.inflate(mActivity, R.layout.activity_home, null);
		home_lv =  (ListView) view.findViewById(R.id.home_lv);
		viewPager = (ViewPager)view.findViewById(R.id.viewpager);
		pointGroup = (LinearLayout) view.findViewById(R.id.point_group);
		iamgeDesc = (TextView) view.findViewById(R.id.image_desc);
		
	
		//图片轮播
		iamgeDesc.setText(imageDescriptions[0]);
		
		initViewPagerData();
		
		myadapter = new MyPagerAdapter();
		viewPager.setAdapter(myadapter);
		
		home_lv.setAdapter(new Myadapter());
		
		home_lv.setOnItemClickListener(this);
		
		viewPager.setCurrentItem(Integer.MAX_VALUE/2 -(Integer.MAX_VALUE/2%imageList.size())) ;
		 
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面切换后调用 
			 * position  新的页面位置
			 */
			public void onPageSelected(int position) {
				position = position % imageList.size();
				// 设置文字描述内容
				iamgeDesc.setText(imageDescriptions[position]);
				// 改变指示点的状态
				// 把当前点enbale 为true
				pointGroup.getChildAt(position).setEnabled(true);
				// 把上一个点设为false
				pointGroup.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;

			}
			
			// 页面正在滑动的时候，回调
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			//当页面状态发生变化的时候，回调
			public void onPageScrollStateChanged(int state) {
			}
		});

		/*
		 * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
		 * 发送延时信息，实现循环
		 */
		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 3000);

		// 向FrameLayout中动态添加view
		fl_content.addView(view);
		
	}

	private void initViewPagerData() {
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			// 初始化图片资源
			ImageView image = new ImageView(mActivity);
			image.setBackgroundResource(imageIds[i]);
			imageList.add(image);

			// 添加指示点
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
		 * 获得页面的总数
		 */
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		public Object instantiateItem(ViewGroup container, int position) {
			// 给 container 添加一个view
			container.addView(imageList.get(position % imageList.size()));
			// 返回一个和该view相对的object
			return imageList.get(position % imageList.size());
		}


		//判断 view和object的对应关系 
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		
		 //销毁对应位置上的object
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
	 *            父分类标题
	 * @param type
	 */
	private void toShopAllActivity(String title, String type) {
		Intent toShopAll = new Intent(mActivity, ShopAllActivity.class);
		toShopAll.putExtra("title", title);
		// 当前点击的项的父分类
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
