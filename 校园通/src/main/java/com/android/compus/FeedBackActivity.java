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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 用户信息反馈
 *  ===============================
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener{
		
	private EditText etContent;   //反馈按钮
	private Button btnSubmit;     //提交按钮
	private TextView tv_title;    //标题
	private TextView tv_back;     //返回按钮
	
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
		
		tv_title.setText("意见反馈");
		
		
		super.initView();
	}


	
	/**
	 * 提交用户的反馈信息
	 */
	private void submit() {
		String content = etContent.getText().toString();
		if(content.equals("")) {
			toast("亲，请先写点东西吧");
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
					toast("提交成功, 我们会尽快采纳您的建议");
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("提交失败");
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
