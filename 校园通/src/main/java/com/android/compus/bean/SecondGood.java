package com.android.compus.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class SecondGood extends BmobObject implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String price;
	private String userID;
	private String title;
	private String description;
	private String googType;         //1.Õº È¿‡£¨2.
	private String phone;
	
	private BmobFile image;
	private BmobFile image2;
	private BmobFile image3;
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGoogType() {
		return googType;
	}
	public void setGoogType(String googType) {
		this.googType = googType;
	}
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}
	
	
	
	
}
