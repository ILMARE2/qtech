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
 *  ����: ��������: 
 *  ����ʱ�䣺2015��8��18�� ����3:34:18 
 *  �汾�ţ� 1.0
 * 	��Ȩ����(C) 2015��8��18�� ������ 
 *  ������������ 
 * ===============================
 */
public class GuideActivity extends BaseActivity implements OnClickListener {

	private ViewPager vp_guide; // viewPager
	private Button btn_guide; // ��ʼ���鰴ť
	// ͼƬid
	private int[] imageIds = new int[] { R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3 };
	
	private ArrayList<ImageView> imageLists;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_guide);
		// �ҵ��ؼ�
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
		btn_guide = (Button) findViewById(R.id.btn_guide);
		
		// ��ʼ��Ҫ��ʾ��ImageView
		initImageView();
	}

	@Override
	protected void setListener() {
		
		// ���ü�����
		vp_guide.setOnPageChangeListener(new OnPageChangeListener() {
			// ��ĳһҳ��ѡ��
			@Override
			public void onPageSelected(int position) {
				// ���һҳʱ ����ʼ���鰴ť��ʾ����
				if (position == imageLists.size() - 1) {
					btn_guide.setVisibility(View.VISIBLE);
				} else {
					btn_guide.setVisibility(View.INVISIBLE);
				}
			}

			//page����
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			//����״̬�ı�
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
	
	
	// ��ʼ��viewPagerҪ��ʾ��ͼƬ
	private void initImageView() {
		imageLists = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			imageLists.add(imageView);
		}
	}

	
	
	// ViewPager��������
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


	
	//��ʼ���鰴ť�ĵ���¼�
	@Override
	public void onClick(View v) {
		//д�������ļ�
		SharePreferencesUtils.setBoolean(getApplicationContext(), "is_showGuide", true);
		
		// �л�����
		Intent intent = new Intent(getApplicationContext(),
				LoginActivity.class);
		startActivity(intent);
		finish();
		
	}

}
