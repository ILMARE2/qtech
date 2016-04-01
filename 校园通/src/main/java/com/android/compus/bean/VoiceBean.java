package com.android.compus.bean;
import java.util.ArrayList;

/**
 * ÓïÒôĞÅÏ¢·â×°
 * 
 * @author Kevin
 * 
 */
public class VoiceBean {

	public ArrayList<WSBean> ws;

	public class WSBean {
		public ArrayList<CWBean> cw;
	}

	public class CWBean {
		public String w;
	}
}
