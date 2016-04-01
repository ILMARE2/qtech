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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class NewsActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.tv_bxt_news_title)
	private TextView tv_bxt_news_title;    //新闻标题
	
	@ViewInject(R.id.tv_bxt_news_title2)
	private TextView tv_bxt_news_title2;   //新闻标题二
	
	@ViewInject(R.id.tv_bxt_news_content)
	private TextView tv_bxt_news_content;   //新闻内容
	
	@ViewInject(R.id.tv_bxt_news_depart)
	private TextView tv_bxt_news_depart;    //发表部门
	
	@ViewInject(R.id.tv_bxt_news_describ)
	private TextView tv_bxt_news_describ;   //描述内容
	
	@ViewInject(R.id.tv_bxt_news_time)
	private TextView tv_bxt_news_time;   	//发表时间
	
	@ViewInject(R.id.tv_back)
	private TextView tv_back;     			//返回按钮
	
	@ViewInject(R.id.tv_title)
	private TextView tv_title;   			//标题栏上的标题

	private BXTNews news2;

	@Override
	protected void initData() {
		tv_title.setText("公告详情");
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


	//返回按钮的点击事件
	public void onClick(View v) {
		finish();
	}

}
