package com.android.compus;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.android.compus.fragement.ContentFragement;
import com.android.compus.fragement.LeftMenuFragement;
import com.android.compus.utils.DensityUtils;
import com.android.compus.utils.PromptManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * ===============================
 *  作者: 静静茹她: 
 *  创建时间：2015年8月18日 下午3:34:18 
 *  版本号： 1.0
 *  版权所有(C) 2015年8月18日 
 *  描述： 主界面 
 *  ===============================
 */
public class MainActivity extends SlidingFragmentActivity {

	private static final String FRAGMENT_LEFT_MENU = "left_menu";

	private static final String FRAGMENT_CONTENT = "content";

	public ContentFragement contentFragment;

	private FragmentManager fragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化
		setBehindContentView(R.layout.slidingmenu_left);

		// 获取侧边栏对象
		SlidingMenu slidingMenu = getSlidingMenu();
		// 设置全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置展现模式
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置预留屏幕的宽度
		slidingMenu.setBehindOffset(DensityUtils.dp2px(getApplicationContext(),
				180));

		
		//初始化fragement
		initFragment();
	}

	private void initFragment() {
		
		//初始化fragement
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// 填充布局
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragement(),
				FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragement(),
				FRAGMENT_CONTENT);
		// 提交事务
		transaction.commit();
	}

	// 主页按钮的点击事件
	public void onBackPressed() {
		// 显示退出对话框
		PromptManager.showExitSystem(this);
	}
}
