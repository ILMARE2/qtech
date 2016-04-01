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
 * =============================== ����: ��������: ����ʱ�䣺2015��8��27�� ����7:10:09 �汾�ţ� 1.0
 * ��Ȩ����(C) 2015��8��27�� ������ ===============================
 */
public class SchoolInfoActivity extends BaseActivity {

	private WebView webview;
	private File file2;
	private SchoolInfo schoolInfo;
	private ProgressBar pbProgressBar; // �Ի���

	private TextView tv_back; // ���˰�ť
	private TextView tv_forward; // ǰ����ť
	private TextView tv_title; // ����

	@Override
	protected void initData() {

		tv_title.setText("ѧУ���");
		tv_back.setText("����");

		BmobQuery<SchoolInfo> query = new BmobQuery<SchoolInfo>();

		query.getObject(getApplicationContext(), "3515f183a8",
				new GetListener<SchoolInfo>() {

					@Override
					public void onSuccess(SchoolInfo arg0) {
						schoolInfo = arg0;
						// �����Ǹ���ҳ
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
		// ���ü���
		webview.setWebViewClient(new WebViewClient() {
			// ҳ�濪ʼ����
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pbProgressBar.setVisibility(View.VISIBLE);
			}

			// ҳ����ؽ���
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pbProgressBar.setVisibility(View.GONE);
			}

			/**
			 * ������ת�����Ӷ����ڴ˷����лص�
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

		// �õ�setting
		// WebSettings settings = webview.getSettings();
		// settings.setJavaScriptEnabled(true); // ����֧��javascript
		// settings.setSupportZoom(true); // ֧������
		// settings.setBuiltInZoomControls(true); // ��ʾ�Ŵ���С��ť
		// settings.setUseWideViewPort(true); // ֧��˫������
		// settings.setLoadWithOverviewMode(true);

		// File file = new File("/sdcard/qtech");
		// if (!file.exists() || !file.isDirectory()) {
		// file.mkdirs();// �����ļ���
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
		// �ʲ������� ����assetĿ¼
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
