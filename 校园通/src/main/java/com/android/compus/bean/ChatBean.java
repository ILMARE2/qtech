package com.android.compus.bean;
/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天数据的实体类
 *  ===============================
 */
public class ChatBean {

	public String text;     // 
	public boolean isAsker;  // true表示提问者

	public int imageId = -1;  //图片ID 

	public ChatBean(String text, boolean isAsker, int imageId) {
		this.text = text;
		this.isAsker = isAsker;
		this.imageId = imageId;
	}

}
