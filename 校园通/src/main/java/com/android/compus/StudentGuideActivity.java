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
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.android.compus.bean.SchoolInfo;
import com.android.compus.bean.StudentGuide;

/**
 * ===============================
 *  ����: ��������:
 *  ����ʱ�䣺2015��8��28�� ����4:10:09 
 *  �汾�ţ� 2.3
 *  ��Ȩ����(C) 2015��8��27�� 
 *  ������ 
 * ===============================
 */
public class StudentGuideActivity extends BaseActivity {

	private WebView webview;
	private File file2;
	private StudentGuide studentGuide;
	private ProgressBar pbProgressBar; // �Ի���

	private TextView tv_back; // ���˰�ť
	private TextView tv_forward; // ǰ����ť
	private TextView tv_title; // ����

	@Override
	protected void initData() {

		tv_title.setText("����ָ��");
		tv_back.setText("����");

		BmobQuery<StudentGuide> query = new BmobQuery<StudentGuide>();

		query.getObject(getApplicationContext(), "8a3a174b03",
				new GetListener<StudentGuide>() {
					@Override
					public void onSuccess(StudentGuide arg0) {
						studentGuide = arg0;
						// �����Ǹ���ҳ
						webview.loadUrl(studentGuide.getStudent_guide().getFileUrl());
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

//		 File file = new File("/sdcard/qtech");
//		 if (!file.exists() || !file.isDirectory()) {
//		 file.mkdirs();// �����ļ���
//		 }
//		 file2 = new File(file,"student_guide.html");
//		 if (!file2.exists()) {
//		 copyfile();
//		 }

		super.initView();
	}

//	private void copyfile() {
//		System.out.println(file2.getAbsolutePath());
//		// �ʲ������� ����assetĿ¼
//		AssetManager manager = getAssets();
//		try {
//			InputStream is = manager.open("student_guide.html");
//			File file = new File(file2.getAbsolutePath());
//			FileOutputStream fos = new FileOutputStream(file);
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while ((len = is.read(buffer)) != -1) {
//				fos.write(buffer, 0, len);
//			}
//			fos.flush();
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
