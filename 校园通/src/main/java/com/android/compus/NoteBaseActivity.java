package com.android.compus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
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
