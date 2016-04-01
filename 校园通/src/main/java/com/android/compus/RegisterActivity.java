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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ �û�ע��
 *  ===============================
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

	private Button btnReg;          //ע�ᰴť
	private EditText etUsername;    //�û���
	private EditText etPassword;    //����
	private EditText etComfirmPsd;  //ȷ������
	private EditText etPhone;       //�绰����

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


	//ע�ᰴť�ĵ���¼�
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reg_now:
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			comfirmPsd = etComfirmPsd.getText().toString();
			phone = etPhone.getText().toString();
			if(!Util.isNetworkConnected(this)) {
				toast("û�����磬������������");
			} else if (username.equals("") || password.equals("")
					|| comfirmPsd.equals("") || phone.equals("")) {
				toast("�뽫��Ϣ��д������");
			} else if (!comfirmPsd.equals(password)) {
				toast("�����������벻һ��");
			} else if(!Util.isPhoneNumberValid(phone)) {
				toast("��������ȷ���ֻ�����");
			}else {
				// ��ʼ�ύע����Ϣ
				User bu = new User();
				bu.setUsername(username);
				bu.setPassword(password);
								
				//�û�ע��,������dmob���Ʒ����ṩ�ķ�����������������user���в���һ������
				//���� �ɹ�����onSuccess����   ʧ�ܵ���onFailure������
				bu.signUp(this, new InsertListener() {      
					public void onSuccess() {						
						saveUserInfo();
					}
					
					public void onFailure(String msg) {
						toast("�û����Ѵ��ڣ��������ְ�");
					}
				});
			}
			break;
		default:
			break;
		}
	}

	
	//�������б����û���Ϣ
	protected void saveUserInfo() {
		//�����û���Ϣ
		UserInfo userinfo=new UserInfo();
		userinfo.setPhone(phone);
		userinfo.setUserName(username);
		
		userinfo.save(getApplicationContext(), new SaveListener() {
			
			public void onSuccess() {
				toast("ע��ɹ��������ǻ�֮�ã�");
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
