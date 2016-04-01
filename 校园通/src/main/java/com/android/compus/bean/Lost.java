package com.android.compus.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ʧ������ʵ����
 *  ===============================
 */
public class Lost extends BmobObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String describe;
	private String phone;
	private String publisher;           //�ĸ��û�������
	
	private BmobFile image;
	private BmobFile image2;
	private BmobFile image3;           //ͼƬ����
	
	
	
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}
	public BmobFile getImage2() {
		return image2;
	}
	public void setImage2(BmobFile image2) {
		this.image2 = image2;
	}
	public BmobFile getImage3() {
		return image3;
	}
	public void setImage3(BmobFile image3) {
		this.image3 = image3;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
