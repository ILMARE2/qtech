package com.android.compus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class NoteBaseActivity extends Activity {
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
	}

	public void startActivity(Class<?> paramClass) {
		startActivity(new Intent(this, paramClass));
	}
}
