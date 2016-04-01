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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * �������ҵ���Ϣ����
 *  ===============================
 */
public class MineInfoActivity extends BaseActivity {
	
	protected static final int MINE_INFO_FINISH_FIND_USER = 1;

	@ViewInject(R.id.tv_mineinfo_username)
	private TextView tvUsername;            //�û���
	
	@ViewInject(R.id.tv_mineinfo_school)
	private TextView tvSchool;             //ѧУ
	
	@ViewInject(R.id.tv_mineinfo_cademy)
	private TextView tvCademy;             //ѧԺ
	
	@ViewInject(R.id.tv_mineinfo_dorpart)
	private TextView tvDorPart;            //����¥
	
	@ViewInject(R.id.tv_mineinfo_dornum)
	private TextView tvDorNum;             //���Һ�
	
	@ViewInject(R.id.tv_mineinfo_phone)
	private TextView tvPhone;              //�绰
	
	@ViewInject(R.id.tv_mineinfo_qq)
	private TextView tvQQ;                //qq
	
	private UserInfo curUser ;           //��ǰ��¼�û�
	
	
	//��ȡ����ǰ�û���Ϣ�����³�ʼ������
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

	//��ʼ������
	@Override
	protected void initView() {
		setContentView(R.layout.activity_mine_info);
		ViewUtils.inject(this);
		
		//��õ�ǰ��¼�û�����Ϣ
		getCurUser();
		super.initView();
	}

	//��ʼ����������
	@Override
	protected void initData() {
		//��������
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

	//��ȡ��ǰ���û���Ϣ
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
				toast(" ��ȡ��ǰ�û�ʧ�ܣ�");
			}
		});
	}
	
	//�༭��ť����¼�
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
	
	//���ذ�ť����¼�
	public void clickBack(View v) {
		finish();
	}
	
	//������˾
	private void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	
	
}