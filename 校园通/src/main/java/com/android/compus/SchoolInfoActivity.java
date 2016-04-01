package com.android.compus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

import com.android.compus.bean.SchoolInfo;

/**
 * =============================== 作者: 静静茹她: 创建时间：2015年8月27日 下午7:10:09 版本号： 1.0
 * 版权所有(C) 2015年8月27日 描述： ===============================
 */
public class SchoolInfoActivity extends BaseActivity {

	private WebView webview;
	private File file2;
	private SchoolInfo schoolInfo;
	private ProgressBar pbProgressBar; // 对话框

	private TextView tv_back; // 后退按钮
	private TextView tv_forward; // 前进按钮
	private TextView tv_title; // 标题

	@Override
	protected void initData() {

		tv_title.setText("学校简介");
		tv_back.setText("返回");

		BmobQuery<SchoolInfo> query = new BmobQuery<SchoolInfo>();

		query.getObject(getApplicationContext(), "3515f183a8",
				new GetListener<SchoolInfo>() {

					@Override
					public void onSuccess(SchoolInfo arg0) {
						schoolInfo = arg0;
						// 加载那个网页
						webview.loadUrl(schoolInfo.getSchoolInfo().getFileUrl());
					}

					public void onFailure(int arg0, String arg1) {

					}
				});

		super.initData();
	}

	@Override
	protected void setListener() {
		tv_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
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

		super.setListener();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_grade_query);
		webview = (WebView) findViewById(R.id.wv_query);

		pbProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_forward = (TextView) findViewById(R.id.tv_edit);
		tv_title = (TextView) findViewById(R.id.tv_title);

		// 拿到setting
		// WebSettings settings = webview.getSettings();
		// settings.setJavaScriptEnabled(true); // 设置支持javascript
		// settings.setSupportZoom(true); // 支持缩放
		// settings.setBuiltInZoomControls(true); // 显示放大缩小按钮
		// settings.setUseWideViewPort(true); // 支持双击缩放
		// settings.setLoadWithOverviewMode(true);

		// File file = new File("/sdcard/qtech");
		// if (!file.exists() || !file.isDirectory()) {
		// file.mkdirs();// 创建文件夹
		// }
		//
		// file2 = new File(file,"school.html");
		// if (!file2.exists()) {
		// copyfile();
		// }

		super.initView();
	}

	private void copyfile() {

		System.out.println(file2.getAbsolutePath());
		// 资产管理器 管理asset目录
		AssetManager manager = getAssets();
		try {
			InputStream is = manager.open("school.html");
			File file = new File(file2.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
