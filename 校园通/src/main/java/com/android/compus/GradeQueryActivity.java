package com.android.compus;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 成绩查询
 *  ===============================
 */
public class GradeQueryActivity extends BaseActivity implements OnClickListener {

	private WebView webview;
	private ProgressBar pbProgressBar;  //对话框

	private TextView tv_back;         //后退按钮
	private TextView tv_forward;      //前进按钮
	private TextView tv_title;        //标题

	private String url = "http://222.195.242.223:8080";      //学校教务处网址。。。。tomcat

	@Override
	protected void initView() {
		setContentView(R.layout.activity_grade_query);
		webview = (WebView) findViewById(R.id.wv_query);
		pbProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_forward = (TextView) findViewById(R.id.tv_edit);
		tv_title = (TextView) findViewById(R.id.tv_title);

		

		super.initView();
	}

	@Override
	protected void initData() {

		tv_title.setText("成绩查询");
		tv_back.setText("后退");
		tv_forward.setText("前进");
		tv_forward.setVisibility(View.VISIBLE);
		
		webview.setInitialScale(50); // 设置默认缩放50%
		// 拿到setting
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true); // 设置支持javascript
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 显示放大缩小按钮
		settings.setUseWideViewPort(true); // 支持双击缩放
		settings.setLoadWithOverviewMode(true);
		webview.loadUrl(url);
		super.initData();
	}

	@Override
	protected void setListener() {
		// 设置监听
		webview.setWebViewClient(new WebViewClient() {
			// 页面开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pbProgressBar.setVisibility(View.VISIBLE);
			}
			// 页面加载结束
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pbProgressBar.setVisibility(View.GONE);
			}

			/**
			 * 所有跳转的链接都会在此方法中回调
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				return true;
			}
		});

		// 后退按钮
		tv_back.setOnClickListener(this);
		// 前进按钮
		tv_forward.setOnClickListener(this);
		super.setListener();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			webview.goBack();
			break;
		case R.id.tv_edit:
			webview.goForward();
			break;
		default:
			break;
		}

	}

}
