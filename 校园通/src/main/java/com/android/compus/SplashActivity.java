package com.android.compus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.compus.utils.SharePreferencesUtils;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ����Ӧ��ʱ������ҳ��
 *  ===============================
 */
public class SplashActivity extends BaseActivity {

	private LinearLayout ll_mLinearLayout;  //������--�Ӷ�����
	private TextView tv_versionNumber;      //�汾��
	private String versionCode;           
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_splash);
		ll_mLinearLayout = (LinearLayout) findViewById(R.id.ll_mLinearLayout);
		tv_versionNumber=(TextView) findViewById(R.id.tv_versionNumber);
		super.initView();
	}

	@Override
	protected void initData() {
		
		//��ʾ�汾��
		if((versionCode=getVersionCode())!=null){
			tv_versionNumber.setText("�汾�ţ�"+versionCode);
		}
		
		
		// ������ã���������
		if (isNetWorkConnected()) {
			setAnimation();
		} else {
			// ���粻���ã������Ի���
			showNoNetworkDialog();
		}

		// ����һ��Ӧ�ó���Ŀ��ͼ��
		//installShortCut();
		
		super.initData();
	}

	
	/**
	 * ��װ��ݷ�ʽ
	 */
	private void installShortCut() {
		//�ж��Ƿ�װ����ݷ�ʽ
		boolean installedshortcut = SharePreferencesUtils.getBoolean(getApplicationContext(), "installedshortcut", false);
		if(installedshortcut){
			return;
		}else{
			System.out.println("��װ��ݷ�ʽ������");
			//�������밲װ��ݷ�ʽ
			Intent intent = new Intent();
			intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "У԰ͨ");
			intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
					BitmapFactory.decodeResource(getResources(), R.drawable.safe));
		
			Intent homeIntent = new Intent();
			homeIntent.setAction("com.android.compus.main");
			homeIntent.addCategory("android.intent.category.DEFAULT");
			intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, homeIntent);
			sendBroadcast(intent);
			
			SharePreferencesUtils.setBoolean(getApplicationContext(), "installedshortcut", true);
		}
	}
	
	// ��ʾû������ĶԻ���
	private void showNoNetworkDialog() {
		//�����Ի���
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		//���öԻ������
		builder.setTitle("��ʾ��");
		//���öԻ�������
		builder.setMessage("��ǰ���粻���ã������������ã�");
		//���õ�ǰ�Ի��򲻿���ȡ����Ҫô��ȷ����Ҫô��ȡ����ť
		builder.setCancelable(false);
		
		//����ȷ����ť
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {	
				//��ϵͳ���������ý���
			    //�жϰ汾��
				if(android.os.Build.VERSION.SDK_INT > 10 ){
				     //3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
				    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				} else {
				    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
	
			}
		});
		
		//����ȡ����ť
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//�ص���ǰҳ�棬�˳�Ӧ��
				finish();
			}
		});
		
		//��ʾ�Ի���
		builder.create().show();
	}

	// �ж�����
	private boolean isNetWorkConnected() {
		// ���ϵͳ�����ӷ���
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		// ��ÿ��õ�������Ϣ
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		// ���������Ƿ�����
		return (networkInfo!=null&&networkInfo.isConnected());
	}

	// ��Ӷ���
	private void setAnimation() {
		// ���䶯��
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		aa.setFillAfter(true);
		
		//���ü���
		aa.setAnimationListener(new AnimationListener() {
			//������ʼ�¼�
			public void onAnimationStart(Animation animation) {
			}
			//���������¼�
			public void onAnimationRepeat(Animation animation) {
			}
			// �������Ž���
			public void onAnimationEnd(Animation animation) {
				//�ж��Ƿ��һ�ν������ ������뻶ӭ����
				//���ǵ�һ�ν���Ļ�����ֱ�ӽ��� ��¼����
				if(SharePreferencesUtils.getBoolean(getApplicationContext(), "is_showGuide", false)){
					Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent=new Intent(getApplicationContext(),GuideActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
		
		//��������
		ll_mLinearLayout.startAnimation(aa);
	}


    //��ȡ�汾����Ϣ
	private String getVersionCode(){
		PackageManager manager;
		try {
			//��ȡ�汾��Ϣ
			manager=getPackageManager();
			PackageInfo info= manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
