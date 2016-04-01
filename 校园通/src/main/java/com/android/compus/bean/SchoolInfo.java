package com.android.compus.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月27日 下午7:34:49 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月27日
 * 描述： 
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
