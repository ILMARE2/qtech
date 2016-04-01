package com.android.compus.bean;
/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 笔记实体类
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
