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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 添加新闻
 *  ===============================
 */
public class AddNewsActivity extends BaseActivity implements OnClickListener {

	EditText et_title, et_depart, et_describe, et_content;
	TextView btn_save, btn_back;    //返回的保存按钮
	TextView tv_title;

	@Override
	protected void initData() {
		btn_back.setText("返回");
		btn_save.setText("保存");
		btn_save.setVisibility(View.VISIBLE);
		tv_title.setText("添加消息");
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

		// 获得文字内容
		String title = et_title.getText().toString();
		String depart = et_depart.getText().toString();
		String describe = et_describe.getText().toString();
		String content = et_content.getText().toString();

		if (TextUtils.isEmpty(title)) {
			Toast.makeText(getApplicationContext(), "公告内容不能为空！", 1).show();
			return;
		}
		if (TextUtils.isEmpty(depart)) {
			Toast.makeText(getApplicationContext(), "部门内容不能为空！", 1).show();
			return;
		}
		if (TextUtils.isEmpty(describe)) {
			Toast.makeText(getApplicationContext(), "描述内容不能为空！", 1).show();
			return;
		}
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(getApplicationContext(), "标题内容不能为空！", 1).show();
			return;
		}

		// 封装对象
		BXTNews news = new BXTNews();
		news.setContent(content);
		news.setDepart(depart);
		news.setDescribe(describe);
		news.setTitle(title);

		// 调用保存的方法
		news.save(getApplicationContext(), new SaveListener() {
			public void onSuccess() {
				Toast.makeText(getApplicationContext(), "保存成功！", 1).show();
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getApplicationContext(), "保存失败！", 1).show();
			}
		});
	}

}
