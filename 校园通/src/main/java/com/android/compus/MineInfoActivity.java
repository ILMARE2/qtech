package com.android.compus;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.android.compus.bean.UserInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：我的信息界面
 *  ===============================
 */
public class MineInfoActivity extends BaseActivity {
	
	protected static final int MINE_INFO_FINISH_FIND_USER = 1;

	@ViewInject(R.id.tv_mineinfo_username)
	private TextView tvUsername;            //用户名
	
	@ViewInject(R.id.tv_mineinfo_school)
	private TextView tvSchool;             //学校
	
	@ViewInject(R.id.tv_mineinfo_cademy)
	private TextView tvCademy;             //学院
	
	@ViewInject(R.id.tv_mineinfo_dorpart)
	private TextView tvDorPart;            //宿舍楼
	
	@ViewInject(R.id.tv_mineinfo_dornum)
	private TextView tvDorNum;             //寝室号
	
	@ViewInject(R.id.tv_mineinfo_phone)
	private TextView tvPhone;              //电话
	
	@ViewInject(R.id.tv_mineinfo_qq)
	private TextView tvQQ;                //qq
	
	private UserInfo curUser ;           //当前登录用户
	
	
	//获取到当前用户信息是重新初始化界面
	private Handler mHandler = new Handler() {  
		  @Override  
		  public void handleMessage(Message msg) {  
		      switch (msg.what) {
				case MINE_INFO_FINISH_FIND_USER:
					initData();
					break;
				default:
					break;
				}
		  }  
	};

	//初始化界面
	@Override
	protected void initView() {
		setContentView(R.layout.activity_mine_info);
		ViewUtils.inject(this);
		
		//获得当前登录用户的信息
		getCurUser();
		super.initView();
	}

	//初始化界面数据
	@Override
	protected void initData() {
		//设置数据
		if(curUser!=null){
			tvUsername.setText(curUser.getUserName());
			tvSchool.setText(curUser.getSchool());
			tvCademy.setText(curUser.getCademy());
			tvDorPart.setText(curUser.getDorPart());
			tvDorNum.setText(curUser.getDorNum());
			tvPhone.setText(curUser.getPhone());
			tvQQ.setText(curUser.getQQ());
		}
	
		super.initData();
	}

	//获取当前的用户信息
	private void getCurUser() {
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		
		BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
		query.addWhereEqualTo("userName", bmobUser.getUsername());
		query.findObjects(this, new FindListener<UserInfo>() {
			
			@Override
			public void onSuccess(List<UserInfo> object) {
				curUser = object.get(0);
				Message msg = new Message();
				msg.what = MINE_INFO_FINISH_FIND_USER;
				mHandler.sendMessage(msg);
			}

			public void onError(int arg0, String arg1) {
				toast(" 获取当前用户失败！");
			}
		});
	}
	
	//编辑按钮点击事件
	public void clickEdit(View v) {
		if(curUser!=null){
			Intent toEditMineInfo = new Intent(MineInfoActivity.this, MineInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username", curUser.getUserName());
			bundle.putString("school", curUser.getSchool());
			bundle.putString("cademy", curUser.getCademy());
			bundle.putString("dorpart", curUser.getDorPart());
			bundle.putString("dornum", curUser.getDorNum());
			bundle.putString("phone", curUser.getPhone());
			bundle.putString("qq", curUser.getQQ());
			toEditMineInfo.putExtras(bundle);
			startActivityForResult(toEditMineInfo,0);
		}else{
			Intent toEditMineInfo = new Intent(MineInfoActivity.this, MineInfoEditActivity.class);
			startActivityForResult(toEditMineInfo,0);
		}
	}
			
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getCurUser();
	}
	
	//返回按钮点击事件
	public void clickBack(View v) {
		finish();
	}
	
	//弹出吐司
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	
	
}