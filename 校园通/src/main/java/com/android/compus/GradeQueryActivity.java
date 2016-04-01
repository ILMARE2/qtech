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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ �ɼ���ѯ
 *  ===============================
 */
public class GradeQueryActivity extends BaseActivity implements OnClickListener {

	private WebView webview;
	private ProgressBar pbProgressBar;  //�Ի���

	private TextView tv_back;         //���˰�ť
	private TextView tv_forward;      //ǰ����ť
	private TextView tv_title;        //����

	private String url = "http://222.195.242.223:8080";      //ѧУ������ַ��������tomcat

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

		tv_title.setText("�ɼ���ѯ");
		tv_back.setText("����");
		tv_forward.setText("ǰ��");
		tv_forward.setVisibility(View.VISIBLE);
		
		webview.setInitialScale(50); // ����Ĭ������50%
		// �õ�setting
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true); // ����֧��javascript
		settings.setSupportZoom(true); // ֧������
		settings.setBuiltInZoomControls(true); // ��ʾ�Ŵ���С��ť
		settings.setUseWideViewPort(true); // ֧��˫������
		settings.setLoadWithOverviewMode(true);
		webview.loadUrl(url);
		super.initData();
	}

	@Override
	protected void setListener() {
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

		// ���˰�ť
		tv_back.setOnClickListener(this);
		// ǰ����ť
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
