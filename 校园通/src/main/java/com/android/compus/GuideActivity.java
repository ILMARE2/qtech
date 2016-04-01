package com.android.compus;

import java.util.ArrayList;

import com.android.compus.utils.SharePreferencesUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * ===============================
 *  作者: 静静茹她: 
 *  创建时间：2015年8月18日 下午3:34:18 
 *  版本号： 1.0
 * 	版权所有(C) 2015年8月18日 描述： 
 *  新手引导界面 
 * ===============================
 */
public class GuideActivity extends BaseActivity implements OnClickListener {

	private ViewPager vp_guide; // viewPager
	private Button btn_guide; // 开始体验按钮
	// 图片id
	private int[] imageIds = new int[] { R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3 };
	
	private ArrayList<ImageView> imageLists;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_guide);
		// 找到控件
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
		btn_guide = (Button) findViewById(R.id.btn_guide);
		
		// 初始化要显示的ImageView
		initImageView();
	}

	@Override
	protected void setListener() {
		
		// 设置监听器
		vp_guide.setOnPageChangeListener(new OnPageChangeListener() {
			// 当某一页被选中
			@Override
			public void onPageSelected(int position) {
				// 最后一页时 将开始体验按钮显示出来
				if (position == imageLists.size() - 1) {
					btn_guide.setVisibility(View.VISIBLE);
				} else {
					btn_guide.setVisibility(View.INVISIBLE);
				}
			}

			//page划动
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			//滑动状态改变
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		btn_guide.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		vp_guide.setAdapter(new MyPagerAdapter());
		super.initData();
	}
	
	
	// 初始化viewPager要显示的图片
	private void initImageView() {
		imageLists = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			imageLists.add(imageView);
		}
	}

	
	
	// ViewPager的适配器
	private class MyPagerAdapter extends PagerAdapter {

		public int getCount() {
			return imageLists.size();
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);

		}

		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageLists.get(position));
			return imageLists.get(position);
		}

	}


	
	//开始体验按钮的点击事件
	@Override
	public void onClick(View v) {
		//写入配置文件
		SharePreferencesUtils.setBoolean(getApplicationContext(), "is_showGuide", true);
		
		// 切换界面
		Intent intent = new Intent(getApplicationContext(),
				LoginActivity.class);
		startActivity(intent);
		finish();
		
	}

}
