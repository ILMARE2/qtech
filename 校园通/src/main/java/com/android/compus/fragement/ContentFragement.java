package com.android.compus.fragement;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.compus.R;
import com.android.compus.pager.BasePager;
import com.android.compus.pager.HomePager;
import com.android.compus.pager.LostFoundPager;
import com.android.compus.pager.NewsPager;
import com.android.compus.pager.SecondGoodPager;
import com.android.compus.pager.ToolsPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentFragement extends BaseFragement implements OnClickListener {


	@ViewInject(R.id.btn_home)
	private LinearLayout btn_home;        //��ҳ
	
	@ViewInject(R.id.btn_news)
	private LinearLayout btn_news;        //���񹫸�
	
	@ViewInject(R.id.btn_lostfound)
	private LinearLayout btn_lostfound;   //�������� 
	
	@ViewInject(R.id.btn_secondhands)
	private LinearLayout btn_secondhands; //���ֽ���
	
	@ViewInject(R.id.btn_tools)
	private LinearLayout btn_tools;       //ʵ�ù���

	@ViewInject(R.id.vp_content)
	private ViewPager vp_content;         // �м����ݵ�ViewPager

	
	public ArrayList<BasePager> viewList;
	private HomePager homePager;

	
	//������oncreteView
	public View initView() {
		
		View view = View.inflate(mActivity, R.layout.fragement_content, null);
		//��ʼ�����ֿؼ�
		ViewUtils.inject(this, view);
		//���ü���
		setListener();
		return view;
	}

	private void setListener() {
		// ���ü���
		btn_home.setOnClickListener(this);
		btn_lostfound.setOnClickListener(this);
		btn_news.setOnClickListener(this);
		btn_secondhands.setOnClickListener(this);
		btn_tools.setOnClickListener(this);
	}

	// ��ʼ������
	protected void initData() {
		
		//��ʼ������
		homePager = new HomePager(mActivity);
		viewList = new ArrayList<BasePager>();
		viewList.add(homePager);
		viewList.add(new NewsPager(mActivity));
		viewList.add(new SecondGoodPager(mActivity));
		viewList.add(new LostFoundPager(mActivity));
		viewList.add(new ToolsPager(mActivity));
		
		// ����������
		vp_content.setAdapter(new MyAdapter());

		// ����ѡ�е�1ҳ
		vp_content.setCurrentItem(0);
		
		//��ʼ����һҳ������
		viewList.get(0).initSuccessView();
		
		//������ɫ
		btn_home.setBackgroundColor(0xff8AC8E4);
		
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				//ҳ��ѡ�к��ʼ������
				viewList.get(arg0).initSuccessView();
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	
	//����¼�
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			changeColor(btn_home);
			vp_content.setCurrentItem(0,false);
			break;
		case R.id.btn_news:
			changeColor(btn_news);
			vp_content.setCurrentItem(1,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_secondhands:
			changeColor(btn_secondhands);
			vp_content.setCurrentItem(2,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_lostfound:
			changeColor(btn_lostfound);
			vp_content.setCurrentItem(3,false);
			homePager.isRunning=false;
			break;
		case R.id.btn_tools:
			changeColor(btn_tools);
			vp_content.setCurrentItem(4,false);
			homePager.isRunning=false;
			break;
		default:
			break;
		}
	}

	private void changeColor(LinearLayout ll_selector) {
		
		//��������ɫ��ʼ��
		btn_home.setBackgroundColor(0xffE4E1E4);
		btn_lostfound.setBackgroundColor(0xffE4E1E4);
		btn_news.setBackgroundColor(0xffE4E1E4);
		btn_secondhands.setBackgroundColor(0xffE4E1E4);
		btn_tools.setBackgroundColor(0xffE4E1E4);
		//�ı�Ŀ��ؼ���ɫ
		ll_selector.setBackgroundColor(0xff8AC8E4);;
	}

	// ViewPager��
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position).rootview);
			return viewList.get(position).rootview;
		}

	}

}
