package com.android.compus.pager;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.compus.CalendarActivity;
import com.android.compus.GradeQueryActivity;
import com.android.compus.LocateActivity;
import com.android.compus.MyNoteMainActivity;
import com.android.compus.R;
import com.android.compus.SchoolInfoActivity;
import com.android.compus.StudentGuideActivity;
import com.android.compus.VoiceRobotActiviity;
import com.android.compus.WeatherScreen;
/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ʵ��С����
 * ===============================
 */
public class ToolsPager extends BasePager implements OnClickListener {

	private String[] tools = { "������Ѷ", "�ֻ��ʼ�", "��ͼ��λ", "�ɼ���ѯ", "��������", "�ֵ�Ͳ","ѧУ���","�������","������" };
	private int[] toolsImage = { R.drawable.tools_0, R.drawable.tools_1,
			R.drawable.tools_2, R.drawable.tools_3, R.drawable.tools_4,
			R.drawable.tools_5 ,R.drawable.tools_6,R.drawable.tools_7,R.drawable.tools_8};

	ImageView[] ivs = new ImageView[9];
	TextView[] tvs = new TextView[9];
	RelativeLayout[] rls = new RelativeLayout[9];

	
    private boolean isopent = false;
	private Camera camera;
	
	public ToolsPager(Activity activity) {
		super(activity);

	}

	@Override
	public void initSuccessView() {
		state = STATE_SUCCESS;
		show();

		tv_title.setText("ʵ��С����");

		View view = View.inflate(mActivity, R.layout.tools_item, null);

		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);
		ivs[4] = (ImageView) view.findViewById(R.id.iv_5);
		ivs[5] = (ImageView) view.findViewById(R.id.iv_6);
		ivs[6] = (ImageView) view.findViewById(R.id.iv_7);
		ivs[7] = (ImageView) view.findViewById(R.id.iv_8);
		ivs[8] = (ImageView) view.findViewById(R.id.iv_9);
		
		tvs[0] = (TextView) view.findViewById(R.id.tv_1);
		tvs[1] = (TextView) view.findViewById(R.id.tv_2);
		tvs[2] = (TextView) view.findViewById(R.id.tv_3);
		tvs[3] = (TextView) view.findViewById(R.id.tv_4);
		tvs[4] = (TextView) view.findViewById(R.id.tv_5);
		tvs[5] = (TextView) view.findViewById(R.id.tv_6);
		tvs[6] = (TextView) view.findViewById(R.id.tv_7);
		tvs[7] = (TextView) view.findViewById(R.id.tv_8);
		tvs[8] = (TextView) view.findViewById(R.id.tv_9);

		rls[0] = (RelativeLayout) view.findViewById(R.id.rl_1);
		rls[1] = (RelativeLayout) view.findViewById(R.id.rl_2);
		rls[2] = (RelativeLayout) view.findViewById(R.id.rl_3);
		rls[3] = (RelativeLayout) view.findViewById(R.id.rl_4);
		rls[4] = (RelativeLayout) view.findViewById(R.id.rl_5);
		rls[5] = (RelativeLayout) view.findViewById(R.id.rl_6);
		rls[6] = (RelativeLayout) view.findViewById(R.id.rl_7);
		rls[7] = (RelativeLayout) view.findViewById(R.id.rl_8);
		rls[8] = (RelativeLayout) view.findViewById(R.id.rl_9);


		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText(tools[i]);
		}

		for (int i = 0; i < tvs.length; i++) {
			ivs[i].setImageResource(toolsImage[i]);
		}

		for (int i = 0; i < tvs.length; i++) {
			rls[i].setOnClickListener(this);
		}

		// ��FrameLayout�ж�̬���view
		fl_content.addView(view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_1:  //����Ԥ��
			Intent intent=new Intent(mActivity,WeatherScreen.class);
			mActivity.startActivity(intent);
			break;
		case R.id.rl_2:  //�ֻ��ʼ�
			Intent NoteIntent=new Intent(mActivity,MyNoteMainActivity.class);
			mActivity.startActivity(NoteIntent);
			break;
		case R.id.rl_3: //��ͼ��λ
			Intent locateIntent=new Intent(mActivity,LocateActivity.class);
			mActivity.startActivity(locateIntent);
			break;
		case R.id.rl_4:  //�ɼ���ѯ
			Intent grageIntent=new Intent(mActivity,GradeQueryActivity.class);
			mActivity.startActivity(grageIntent);
			break;
		case R.id.rl_5:  //��������
			Intent robotIntent=new Intent(mActivity,VoiceRobotActiviity.class);
			mActivity.startActivity(robotIntent);
			break;
		case R.id.rl_6:  //�ֵ�Ͳ
			if (!isopent) {
                Toast.makeText(mActivity, "���Ѿ������ֵ�Ͳ", 0)
                        .show();
                camera = Camera.open();
                Parameters params = camera.getParameters();
                params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview(); // ��ʼ����
                isopent = true;
            } else {
                Toast.makeText(mActivity, "�ر����ֵ�Ͳ",
                        Toast.LENGTH_SHORT).show();
                camera.stopPreview(); // �ص�����
                camera.release(); // �ص������
                isopent = false;
            }
			break;
		case R.id.rl_7:  //ѧУ���
			Intent schoolIntent=new Intent(mActivity,SchoolInfoActivity.class);
			mActivity.startActivity(schoolIntent);
			break;
		case R.id.rl_8:  //�������
			Intent guideIntent=new Intent(mActivity,StudentGuideActivity.class);
			mActivity.startActivity(guideIntent);
			break;
		case R.id.rl_9:  //����
			Intent calendarIntent =new Intent(mActivity,CalendarActivity.class);
			mActivity.startActivity(calendarIntent);
			break;
		default:
			break;
		}
	}

}
