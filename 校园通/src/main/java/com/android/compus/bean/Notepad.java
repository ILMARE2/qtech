package com.android.compus.bean;
/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ �ʼ�ʵ����
 *  ===============================
 */
public class Notepad {
	public String content;
	public String data;
	public String id;
	public String title;

	public String getContent() {
		return this.content;
	}

	public String getTitle() {
		return this.title;
	}

	public String getdata() {
		return this.data;
	}

	public String getid() {
		return this.id;
	}

	public void setContent(String paramString) {
		this.content = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public void setdata(String paramString) {
		this.data = paramString;
	}

	public void setid(String paramString) {
		this.id = paramString;
	}
}
