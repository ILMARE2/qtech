package com.android.compus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.compus.bean.BXTNews;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;



/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class NewsActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.tv_bxt_news_title)
	private TextView tv_bxt_news_title;    //���ű���
	
	@ViewInject(R.id.tv_bxt_news_title2)
	private TextView tv_bxt_news_title2;   //���ű����
	
	@ViewInject(R.id.tv_bxt_news_content)
	private TextView tv_bxt_news_content;   //��������
	
	@ViewInject(R.id.tv_bxt_news_depart)
	private TextView tv_bxt_news_depart;    //������
	
	@ViewInject(R.id.tv_bxt_news_describ)
	private TextView tv_bxt_news_describ;   //��������
	
	@ViewInject(R.id.tv_bxt_news_time)
	private TextView tv_bxt_news_time;   	//����ʱ��
	
	@ViewInject(R.id.tv_back)
	private TextView tv_back;     			//���ذ�ť
	
	@ViewInject(R.id.tv_title)
	private TextView tv_title;   			//�������ϵı���

	private BXTNews news2;

	@Override
	protected void initData() {
		tv_title.setText("��������");
		Bundle bundle = getIntent().getExtras();
		news2 = (BXTNews) bundle.getSerializable("news");
		tv_bxt_news_title2.setText(news2.getTitle());
		tv_bxt_news_title.setText(news2.getTitle());
		tv_bxt_news_content.setText(news2.getContent());
		tv_bxt_news_depart.setText(news2.getDepart());
		tv_bxt_news_describ.setText(news2.getDescribe());
		tv_bxt_news_time.setText(getIntent().getStringExtra("time"));

		super.initData();
	}

	@Override
	protected void setListener() {
		tv_back.setOnClickListener(this);
		super.setListener();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_bxt_news);
		ViewUtils.inject(this);
		super.initView();
	}


	//���ذ�ť�ĵ���¼�
	public void onClick(View v) {
		finish();
	}

}
