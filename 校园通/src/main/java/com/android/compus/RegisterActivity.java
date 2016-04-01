package com.android.compus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.InsertListener;
import cn.bmob.v3.listener.SaveListener;

import com.android.compus.bean.User;
import com.android.compus.bean.UserInfo;
import com.android.compus.utils.Util;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 用户注册
 *  ===============================
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

	private Button btnReg;          //注册按钮
	private EditText etUsername;    //用户名
	private EditText etPassword;    //密码
	private EditText etComfirmPsd;  //确认密码
	private EditText etPhone;       //电话号码

	private String username = null;
	private String password = null;
	private String comfirmPsd = null;  
	private String phone = null;

	
	@Override
	protected void initView() {
		
		setContentView(R.layout.activity_reg);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
		etPhone = (EditText) findViewById(R.id.et_phone);
		btnReg = (Button) findViewById(R.id.btn_reg_now);
		
		super.initView();
	}

	@Override
	protected void setListener() {
		btnReg.setOnClickListener(this);
		super.setListener();
	}


	//注册按钮的点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reg_now:
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			comfirmPsd = etComfirmPsd.getText().toString();
			phone = etPhone.getText().toString();
			if(!Util.isNetworkConnected(this)) {
				toast("没有网络，请检查网络链接");
			} else if (username.equals("") || password.equals("")
					|| comfirmPsd.equals("") || phone.equals("")) {
				toast("请将信息填写完整！");
			} else if (!comfirmPsd.equals(password)) {
				toast("两次密码输入不一致");
			} else if(!Util.isPhoneNumberValid(phone)) {
				toast("请输入正确的手机号码");
			}else {
				// 开始提交注册信息
				User bu = new User();
				bu.setUsername(username);
				bu.setPassword(password);
								
				//用户注册,这里是dmob的云服务提供的方法，即往服务器的user表中查入一条数据
				//插入 成功调用onSuccess方法   失败调用onFailure方法。
				bu.signUp(this, new InsertListener() {      
					public void onSuccess() {						
						saveUserInfo();
					}
					
					public void onFailure(String msg) {
						toast("用户名已存在，换个名字吧");
					}
				});
			}
			break;
		default:
			break;
		}
	}

	
	//向数据中保存用户信息
	protected void saveUserInfo() {
		//保存用户信息
		UserInfo userinfo=new UserInfo();
		userinfo.setPhone(phone);
		userinfo.setUserName(username);
		
		userinfo.save(getApplicationContext(), new SaveListener() {
			
			public void onSuccess() {
				toast("注册成功，开启智慧之旅！");
				Intent backLogin = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(backLogin);
				RegisterActivity.this.finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
			}
		});
	}
	
	
	
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	};

}
