package com.android.compus.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��27�� ����7:34:49 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��27��
 * ������ 
 *  ===============================
 */
public class SchoolInfo extends BmobObject{

	BmobFile schoolInfo;

	public BmobFile getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(BmobFile schoolInfo) {
		this.schoolInfo = schoolInfo;
	}
	
	
}
