package com.android.compus.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 新闻实体类
 *  ===============================
 */
public class BXTNews extends BmobObject implements Serializable{

	private String title;			//标题
	private String depart;			//讲座主题
	private String describe;			//主 讲 人
	private String content;			//讲座时间
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
