package com.android.compus;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.android.compus.bean.User;
import com.android.compus.bean.UserInfo;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class MineInfoEditActivity extends Activity {
	
	protected static final int MINE_INFO_FINISH_FIND_USER = 0;
	private EditText etUsername;
	private EditText etSchool;
	private EditText etCademy;
	private EditText etDorPart;
	private EditText etDorNum;
	private EditText etPhone;
	private EditText etQQ;
	
	private UserInfo curUser;
	private Bundle bundle;
	private Handler mHandler = new Handler() {  
		  @Override  
		  public void handleMessage(Message msg) {  
		      switch (msg.what) {
				case MINE_INFO_FINISH_FIND_USER:
					initView();
					break;
				default:
					break;
				}
		  }  
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_info_edit);
		
		setCurUser();
	}
	
	private void initView() {
		etUsername = (EditText) findViewById(R.id.et_mineinfo_username);
		etSchool = (EditText) findViewById(R.id.et_mineinfo_school);
		etCademy = (EditText) findViewById(R.id.et_mineinfo_cademy);
		etDorPart = (EditText) findViewById(R.id.et_mineinfo_dorpart);
		etDorNum = (EditText) findViewById(R.id.et_mineinfo_dornum);
		etPhone = (EditText) findViewById(R.id.et_mineinfo_phone);
		etQQ = (EditText) findViewById(R.id.et_mineinfo_qq);
		
		etUsername.setText(curUser.getUserName());
		etSchool.setText(curUser.getSchool());
		etCademy.setText(curUser.getCademy());
		etDorPart.setText(curUser.getDorPart());
		etDorNum.setText(curUser.getDorNum());
		etPhone.setText(curUser.getPhone());
		etQQ.setText(curUser.getQQ());
	}
	
	private void setCurUser() {
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
		
		query.addWhereEqualTo("userName", bmobUser.getUsername());
		query.findObjects(this, new FindListener<UserInfo>() {
			
			@Override
			public void onSuccess(List<UserInfo> object) {
				curUser = object.get(0);
				//toast("��ѯ���û�  " + object.size());
				Message msg = new Message();
				msg.what = MINE_INFO_FINISH_FIND_USER;
				mHandler.sendMessage(msg);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				toast("�ף� ��ȡ��ǰ�û�ʧ��");
			}
		});
		
	}
	
	private void saveUserInfo() {
		if(curUser == null) {
			toast("curUserΪ��");
		} else {
			toast("��ǰ�û�Ϊ  " + curUser.getUserName());
		}
		curUser.setUserName(etUsername.getText().toString());
		curUser.setSchool(etSchool.getText().toString());
		curUser.setCademy(etCademy.getText().toString());
		curUser.setDorPart(etDorPart.getText().toString());
		curUser.setDorNum(etDorNum.getText().toString());
		curUser.setPhone(etPhone.getText().toString());
		curUser.setQQ(etQQ.getText().toString());
		
		//���·��������еķ�����������1.�����ģ�2.����������3������
		curUser.update(this, curUser.getObjectId(), new UpdateListener() {
			public void onSuccess() {
				toast("���³ɹ�");
			}
			public void onFailure(int arg0, String arg1) {
				toast("����ʧ��");
			}
		});
	}
	
	public void clickSave(View v) {
		saveUserInfo();
		finish();
	}
	
	public void clickCancel(View v) {
		finish();
	}
	
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	
}
