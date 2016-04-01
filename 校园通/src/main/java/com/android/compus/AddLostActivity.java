package com.android.compus;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.android.compus.bean.Lost;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���ʧ��������Ϣ
 *  ===============================
 */
public class AddLostActivity extends BaseActivity implements OnClickListener {
	EditText edit_title, edit_photo, edit_describe;
	TextView btn_back, btn_save;
	TextView tv_title;
	String from ;
	String title ;
	String describe;
	String photo;	
	Toast mToast;
	@Override
	protected void initView() {
		setContentView(R.layout.activity_add_lost);

		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_back = (TextView) findViewById(R.id.tv_back);
		btn_save = (TextView) findViewById(R.id.tv_edit);
		
		edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title =
				(EditText) findViewById(R.id.edit_title);

		btn_back.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		
		btn_save.setText("����");
		btn_save.setVisibility(View.VISIBLE);
		
		//��ȡ���� �༭�������
		boolean isEdit=getIntent().getBooleanExtra("isEdit", false);
		if(isEdit){   //�Ǳ༭�Ļ�
			Bundle bundle= getIntent().getExtras();
			Lost lost=(Lost) bundle.getSerializable("lost");
			edit_photo.setText(lost.getPhone());
			edit_describe.setText(lost.getDescribe());
			edit_title.setText(lost.getTitle());
			
			tv_title.setText("�༭");
		}else{
			tv_title.setText("���ʧ������");
		}
		super.initView();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.tv_edit:
			addByType();
			break;
		default:
			break;
		}
	}
   
	//�����������
	private void addByType(){
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		photo = edit_photo.getText().toString();

		if(TextUtils.isEmpty(title)){
			ShowToast("����д����");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("����д����");
			return;
		}
		if(TextUtils.isEmpty(photo)){
			ShowToast("����д�ֻ�");
			return;
		}

		addLost();

	}

	
	//��Ӷ���
	private void addLost(){
		Lost lost = new Lost();
		lost.setDescribe(describe);
		lost.setPhone(photo);
		lost.setTitle(title);
		lost.setPublisher(BmobUser.getCurrentUser(getApplicationContext()).getUsername());
		lost.save(this, new SaveListener() {
			public void onSuccess() {
				ShowToast("ʧ����Ϣ��ӳɹ�!");
				setResult(RESULT_OK);
				finish();
			}
			public void onFailure(int code, String arg0) {
				ShowToast("���ʧ��:"+arg0);
			}
		});
	}
	
	
	//������˾
	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}


}
