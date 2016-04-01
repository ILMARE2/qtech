package com.android.compus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.InsertListener;

import com.android.compus.bean.User;
import com.android.compus.utils.SharePreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 登陆界面
 * ===============================
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.et_username)
	private EditText et_username;   // 用户名
	@ViewInject(R.id.et_password)
	private EditText et_password;   // 密码
	@ViewInject(R.id.btn_login)
	private Button btn_login;       // 登录按钮
	@ViewInject(R.id.btn_register)
	private Button btn_register;    // 注册按钮
	
	//登陆时要显示的对话框
	private ProgressDialog dialog;


	@Override
	protected void initView() {
		setContentView(R.layout.activity_login);
		
		ViewUtils.inject(this);
		
		super.initView();
	}

	@Override
	protected void setListener() {
		//登陆按钮点击事件
		btn_login.setOnClickListener(this);
		//注册按钮点击事件
		btn_register.setOnClickListener(this);
		
		super.setListener();
	}
	
	@Override
	protected void initData() {
		// 初始化用户信息 获取上一次登陆的用户名和密码显示到界面上
		getUserInfoFromLastLogin();
		super.initData();
	}

	//获取上次登录时的用户名和密码并显示到界面上
	private void getUserInfoFromLastLogin() {
		et_password.setText(SharePreferencesUtils.getString(getApplicationContext(),
				"password", null));
		et_username.setText(SharePreferencesUtils.getString(getApplicationContext(),
				"username", null));
	}

	// 点击事件处理
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:      //切换登录，显示对话框 
			showLoginDialog();
			login();
			break;
		case R.id.btn_register:   //切换到注册界面
			Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	
	//登录时显示dialog
	private void showLoginDialog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("登录中..");
		dialog.show();
	}

	//登录
	private void login() {
		//获取用户名和密码
		String username=et_username.getText().toString();
		String password=et_password.getText().toString();
		
		//判断用户名是否为空
		if(TextUtils.isEmpty(username)){
			Toast.makeText(getApplicationContext(), "用户名不能为空!", 1).show();
			return ;
		}
		
		//判断密码是否为空
		if(TextUtils.isEmpty(password)){
			Toast.makeText(getApplicationContext(), "密码不能为空!", 1).show();
			return ;
		}
		
		//封装javabean
		final User user=new User();
		user.setPassword(password);
		user.setUsername(username);
		
		//调用Bmob的方法
		user.login(getApplicationContext(), new InsertListener() {
			//登录成功
			public void onSuccess() {
				//记录用户名和密码
				SharePreferencesUtils.setString(getApplicationContext(), "username", user.getUsername());
				SharePreferencesUtils.setString(getApplicationContext(), "password", user.getPassword());
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "登录成功！", 0).show();
				//跳转到主界面
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
			
			//登录失败
			public void onFailure(String arg0) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "用户名和密码不正确！", 1).show();
			}
		});
	}

}
