package com.android.compus.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * �û�javabean
 * @author zhangchenggeng
 *
 */
public class UserInfo extends BmobObject {

	private String userId;      //�û�ID
	private String userName;    //�û���
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	private String sex;  		// �Ա�
	private String phone; 		// �绰
	private String qq; 			// QQ
	private String school = "�ൺ����ѧ";  // ѧУ
	private String cademy; 		// ѧԺ
	private String dorPart; 	// У��
	private String dorNum; 		// ���Һ�
	private String state = "δ��½"; 		// ��¼״̬
	private String type = "��ͨ�û�";		// �û�����(��ͨ�û������������н���)
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQQ() {
		return qq;
	}

	public void setQQ(String qq) {
		this.qq = qq;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCademy() {
		return cademy;
	}

	public void setCademy(String cademy) {
		this.cademy = cademy;
	}

	public String getDorPart() {
		return dorPart;
	}

	public void setDorPart(String dorPart) {
		this.dorPart = dorPart;
	}

	public String getDorNum() {
		return dorNum;
	}

	public void setDorNum(String dorNum) {
		this.dorNum = dorNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
