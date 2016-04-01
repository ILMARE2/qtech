package com.android.compus.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * ����ʵ���࣬ ʵ�����л�, Activity֮��ʵ�ִ���
 */
public class Shop extends BmobObject implements Serializable{
	
	private static final long serialVersionUID = -8796635595320697255L;
	
	private String userID; 		// ����
	private String type; 		// ����(11�����һ��GridView�еĵ�һ��)
	private String name; 		// ����
	private String location; 	// ����λ��
	private String phone;		// ��ϵ�绰
	private String info; 		// ���
	private String sale; 		// ������Ϣ
	private BmobFile picShop; 	// �̵���ͼ

	public String getUserID() {
		return userID;
	}

	public BmobFile getPicShop() {
		return picShop;
	}

	public void setPicShop(BmobFile picShop) {
		this.picShop = picShop;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

}
