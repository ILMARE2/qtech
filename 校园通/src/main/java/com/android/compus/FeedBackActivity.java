package com.android.compus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.InsertListener;
import cn.bmob.v3.listener.SaveListener;

import com.android.compus.bean.FeedBack;


/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ �û���Ϣ����
 *  ===============================
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener{
		
	private EditText etContent;   //������ť
	private Button btnSubmit;     //�ύ��ť
	private TextView tv_title;    //����
	private TextView tv_back;     //���ذ�ť
	
	@Override
	protected void setListener() {
		btnSubmit.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		super.setListener();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_feedback);
		etContent = (EditText) findViewById(R.id.et_feedback_content);
		btnSubmit = (Button) findViewById(R.id.btn_feedback_submit);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_back=(TextView) findViewById(R.id.tv_back);
		
		tv_title.setText("�������");
		
		
		super.initView();
	}


	
	/**
	 * �ύ�û��ķ�����Ϣ
	 */
	private void submit() {
		String content = etContent.getText().toString();
		if(content.equals("")) {
			toast("�ף�����д�㶫����");
		} else {
			BmobUser user = BmobUser.getCurrentUser(this);
			FeedBack fb = new FeedBack();
			fb.setUsername(user.getUsername());
			fb.setEmail(user.getEmail());
			fb.setContent(content);
		
			fb.save(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					toast("�ύ�ɹ�, ���ǻᾡ��������Ľ���");
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("�ύʧ��");
				}
			});
			
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_feedback_submit:
			submit();
			break;
		case R.id.tv_back:
			finish();
			break;
		default:
			break;
		}
	}
	
	
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	

}
