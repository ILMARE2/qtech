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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ��½����
 * ===============================
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.et_username)
	private EditText et_username;   // �û���
	@ViewInject(R.id.et_password)
	private EditText et_password;   // ����
	@ViewInject(R.id.btn_login)
	private Button btn_login;       // ��¼��ť
	@ViewInject(R.id.btn_register)
	private Button btn_register;    // ע�ᰴť
	
	//��½ʱҪ��ʾ�ĶԻ���
	private ProgressDialog dialog;


	@Override
	protected void initView() {
		setContentView(R.layout.activity_login);
		
		ViewUtils.inject(this);
		
		super.initView();
	}

	@Override
	protected void setListener() {
		//��½��ť����¼�
		btn_login.setOnClickListener(this);
		//ע�ᰴť����¼�
		btn_register.setOnClickListener(this);
		
		super.setListener();
	}
	
	@Override
	protected void initData() {
		// ��ʼ���û���Ϣ ��ȡ��һ�ε�½���û�����������ʾ��������
		getUserInfoFromLastLogin();
		super.initData();
	}

	//��ȡ�ϴε�¼ʱ���û��������벢��ʾ��������
	private void getUserInfoFromLastLogin() {
		et_password.setText(SharePreferencesUtils.getString(getApplicationContext(),
				"password", null));
		et_username.setText(SharePreferencesUtils.getString(getApplicationContext(),
				"username", null));
	}

	// ����¼�����
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:      //�л���¼����ʾ�Ի��� 
			showLoginDialog();
			login();
			break;
		case R.id.btn_register:   //�л���ע�����
			Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	
	//��¼ʱ��ʾdialog
	private void showLoginDialog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("��¼��..");
		dialog.show();
	}

	//��¼
	private void login() {
		//��ȡ�û���������
		String username=et_username.getText().toString();
		String password=et_password.getText().toString();
		
		//�ж��û����Ƿ�Ϊ��
		if(TextUtils.isEmpty(username)){
			Toast.makeText(getApplicationContext(), "�û�������Ϊ��!", 1).show();
			return ;
		}
		
		//�ж������Ƿ�Ϊ��
		if(TextUtils.isEmpty(password)){
			Toast.makeText(getApplicationContext(), "���벻��Ϊ��!", 1).show();
			return ;
		}
		
		//��װjavabean
		final User user=new User();
		user.setPassword(password);
		user.setUsername(username);
		
		//����Bmob�ķ���
		user.login(getApplicationContext(), new InsertListener() {
			//��¼�ɹ�
			public void onSuccess() {
				//��¼�û���������
				SharePreferencesUtils.setString(getApplicationContext(), "username", user.getUsername());
				SharePreferencesUtils.setString(getApplicationContext(), "password", user.getPassword());
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "��¼�ɹ���", 0).show();
				//��ת��������
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
			
			//��¼ʧ��
			public void onFailure(String arg0) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "�û��������벻��ȷ��", 1).show();
			}
		});
	}

}
