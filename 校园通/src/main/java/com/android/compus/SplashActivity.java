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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 进入应用时的闪屏页面
 *  ===============================
 */
public class SplashActivity extends BaseActivity {

	private LinearLayout ll_mLinearLayout;  //根布局--加动画用
	private TextView tv_versionNumber;      //版本号
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
		
		//显示版本号
		if((versionCode=getVersionCode())!=null){
			tv_versionNumber.setText("版本号："+versionCode);
		}
		
		
		// 网络可用，开启动画
		if (isNetWorkConnected()) {
			setAnimation();
		} else {
			// 网络不可用，弹出对话框
			showNoNetworkDialog();
		}

		// 创建一个应用程序的快捷图标
		//installShortCut();
		
		super.initData();
	}

	
	/**
	 * 安装快捷方式
	 */
	private void installShortCut() {
		//判断是否安装过快捷方式
		boolean installedshortcut = SharePreferencesUtils.getBoolean(getApplicationContext(), "installedshortcut", false);
		if(installedshortcut){
			return;
		}else{
			System.out.println("安装快捷方式。。。");
			//启动代码安装快捷方式
			Intent intent = new Intent();
			intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "校园通");
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
	
	// 显示没有网络的对话框
	private void showNoNetworkDialog() {
		//创建对话框
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		//设置对话框标题
		builder.setTitle("提示：");
		//设置对话框内容
		builder.setMessage("当前网络不可用，请检查网络设置！");
		//设置当前对话框不可以取消，要么点确定，要么点取消按钮
		builder.setCancelable(false);
		
		//设置确定按钮
		builder.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {	
				//打开系统的网络设置界面
			    //判断版本号
				if(android.os.Build.VERSION.SDK_INT > 10 ){
				     //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
				    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				} else {
				    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
	
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//关掉当前页面，退出应用
				finish();
			}
		});
		
		//显示对话框
		builder.create().show();
	}

	// 判断网络
	private boolean isNetWorkConnected() {
		// 获得系统的链接服务
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		// 获得可用的网络信息
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		// 返回网络是否链接
		return (networkInfo!=null&&networkInfo.isConnected());
	}

	// 添加动画
	private void setAnimation() {
		// 渐变动画
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		aa.setFillAfter(true);
		
		//设置监听
		aa.setAnimationListener(new AnimationListener() {
			//动画开始事件
			public void onAnimationStart(Animation animation) {
			}
			//动画结束事件
			public void onAnimationRepeat(Animation animation) {
			}
			// 动画播放结束
			public void onAnimationEnd(Animation animation) {
				//判断是否第一次进入软件 是则进入欢迎界面
				//不是第一次进入的话，则直接进入 登录界面
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
		
		//开启动画
		ll_mLinearLayout.startAnimation(aa);
	}


    //获取版本号信息
	private String getVersionCode(){
		PackageManager manager;
		try {
			//获取版本信息
			manager=getPackageManager();
			PackageInfo info= manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
