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
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 添加失物招领信息
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
		
		btn_save.setText("保存");
		btn_save.setVisibility(View.VISIBLE);
		
		//获取类型 编辑还是添加
		boolean isEdit=getIntent().getBooleanExtra("isEdit", false);
		if(isEdit){   //是编辑的话
			Bundle bundle= getIntent().getExtras();
			Lost lost=(Lost) bundle.getSerializable("lost");
			edit_photo.setText(lost.getPhone());
			edit_describe.setText(lost.getDescribe());
			edit_title.setText(lost.getTitle());
			
			tv_title.setText("编辑");
		}else{
			tv_title.setText("添加失物招领");
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
   
	//按照类型添加
	private void addByType(){
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		photo = edit_photo.getText().toString();

		if(TextUtils.isEmpty(title)){
			ShowToast("请填写标题");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("请填写描述");
			return;
		}
		if(TextUtils.isEmpty(photo)){
			ShowToast("请填写手机");
			return;
		}

		addLost();

	}

	
	//添加对象
	private void addLost(){
		Lost lost = new Lost();
		lost.setDescribe(describe);
		lost.setPhone(photo);
		lost.setTitle(title);
		lost.setPublisher(BmobUser.getCurrentUser(getApplicationContext()).getUsername());
		lost.save(this, new SaveListener() {
			public void onSuccess() {
				ShowToast("失物信息添加成功!");
				setResult(RESULT_OK);
				finish();
			}
			public void onFailure(int code, String arg0) {
				ShowToast("添加失败:"+arg0);
			}
		});
	}
	
	
	//弹出吐司
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
