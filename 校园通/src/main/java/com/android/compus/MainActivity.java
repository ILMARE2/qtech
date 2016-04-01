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
 *  ����: ��������: 
 *  ����ʱ�䣺2015��8��18�� ����3:34:18 
 *  �汾�ţ� 1.0
 *  ��Ȩ����(C) 2015��8��18�� 
 *  ������ ������ 
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

		// ��ʼ��
		setBehindContentView(R.layout.slidingmenu_left);

		// ��ȡ���������
		SlidingMenu slidingMenu = getSlidingMenu();
		// ����ȫ������
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// ����չ��ģʽ
		slidingMenu.setMode(SlidingMenu.LEFT);
		// ����Ԥ����Ļ�Ŀ��
		slidingMenu.setBehindOffset(DensityUtils.dp2px(getApplicationContext(),
				180));

		
		//��ʼ��fragement
		initFragment();
	}

	private void initFragment() {
		
		//��ʼ��fragement
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// ��䲼��
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragement(),
				FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragement(),
				FRAGMENT_CONTENT);
		// �ύ����
		transaction.commit();
	}

	// ��ҳ��ť�ĵ���¼�
	public void onBackPressed() {
		// ��ʾ�˳��Ի���
		PromptManager.showExitSystem(this);
	}
}
