package com.android.compus.bean;

import cn.bmob.v3.BmobObject;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ������Ϣʵ����
 *  ===============================
 */
public class FeedBack extends BmobObject {

	private String username;
	private String phone;
	private String email;
	private String content;

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getContent() {
		return content;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
