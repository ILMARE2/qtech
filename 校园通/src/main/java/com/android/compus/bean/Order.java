package com.android.compus.bean;
import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ������Ϣ
 *  ===============================
 */
public class Order extends BmobObject implements Serializable {

	private String userName;
	private String goodID; 		// ��ƷID
	private String goodName;
	private String shopID; 		// �̵�ID
	private String shopName;
	private String count; 		// ����
	private String price;       // �۸�
	private String time;		// ȡ��ʱ��
	private String phone;		// ��ϵ�绰
	private String state = "δȡ��"; 	// ����״̬(��ȡ, δȡ)
	private String tips; 		// ������Ϣ
	
	public String getGoodID() {
		return goodID;
	}
	public String getGoodName() {
		return goodName;
	}
	public String getUserName() {
		return userName;
	}
	public String getShopID() {
		return shopID;
	}
	public String getShopName() {
		return shopName;
	}
	public String getCount() {
		return count;
	}
	public String getPrice() {
		return price;
	}
	public String getTime() {
		return time;
	}
	public String getPhone() {
		return phone;
	}
	public String getState() {
		return state;
	}
	public String getTips() {
		return tips;
	}
	public void setGoodID(String goodID) {
		this.goodID = goodID;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}


}
