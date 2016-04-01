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
 * ������ ��Ʒʵ����
 *  ===============================
 */
public class Good extends BmobObject implements Serializable{
	
	private static final long serialVersionUID = -3248168273019127389L;
	
	//private String id;  ��ƷID, Ĭ��
	
	private String shopID = ""; 		// �̵�ID
	private String shopName = "";		//�̵�����
	private String type = ""; 		    // ����
	private String name = ""; 		    // ����
	private String price = ""; 		    // �۸�
	private BmobFile picGood ; 	// ��Ʒ��ͼ
	
	public Good(String name, String price) {
		this.name = name;
		this.price  = price;
	}
	
	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
