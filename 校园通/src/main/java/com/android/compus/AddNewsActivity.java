package com.android.compus;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.android.compus.bean.BXTNews;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ �������
 *  ===============================
 */
public class AddNewsActivity extends BaseActivity implements OnClickListener {

	EditText et_title, et_depart, et_describe, et_content;
	TextView btn_save, btn_back;    //���صı��水ť
	TextView tv_title;

	@Override
	protected void initData() {
		btn_back.setText("����");
		btn_save.setText("����");
		btn_save.setVisibility(View.VISIBLE);
		tv_title.setText("�����Ϣ");
		super.initData();
	}

	@Override
	protected void setListener() {
		btn_save.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		super.setListener();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_add_news);
		et_title = (EditText) this.findViewById(R.id.et_title);
		et_depart = (EditText) this.findViewById(R.id.et_depart);
		et_describe = (EditText) this.findViewById(R.id.et_describe);
		et_content = (EditText) this.findViewById(R.id.et_content);
		btn_save = (TextView) this.findViewById(R.id.tv_edit);
		btn_back = (TextView) this.findViewById(R.id.tv_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		super.initView();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();

			break;
		case R.id.tv_edit:
			saveNews();
			break;
		default:
			break;
		}

	}

	private void saveNews() {

		// �����������
		String title = et_title.getText().toString();
		String depart = et_depart.getText().toString();
		String describe = et_describe.getText().toString();
		String content = et_content.getText().toString();

		if (TextUtils.isEmpty(title)) {
			Toast.makeText(getApplicationContext(), "�������ݲ���Ϊ�գ�", 1).show();
			return;
		}
		if (TextUtils.isEmpty(depart)) {
			Toast.makeText(getApplicationContext(), "�������ݲ���Ϊ�գ�", 1).show();
			return;
		}
		if (TextUtils.isEmpty(describe)) {
			Toast.makeText(getApplicationContext(), "�������ݲ���Ϊ�գ�", 1).show();
			return;
		}
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(getApplicationContext(), "�������ݲ���Ϊ�գ�", 1).show();
			return;
		}

		// ��װ����
		BXTNews news = new BXTNews();
		news.setContent(content);
		news.setDepart(depart);
		news.setDescribe(describe);
		news.setTitle(title);

		// ���ñ���ķ���
		news.save(getApplicationContext(), new SaveListener() {
			public void onSuccess() {
				Toast.makeText(getApplicationContext(), "����ɹ���", 1).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getApplicationContext(), "����ʧ�ܣ�", 1).show();
			}
		});
	}

}
