package com.android.compus.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ����ʵ����
 *  ===============================
 */
public class BXTNews extends BmobObject implements Serializable{

	private String title;			//����
	private String depart;			//��������
	private String describe;			//�� �� ��
	private String content;			//����ʱ��
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
