package com.android.compus.bean;
import java.util.Calendar;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 笔记日期 实体类
 *  ===============================
 */
public class Date {
	public String getDate() {
		Calendar localCalendar = Calendar.getInstance();
		int i = localCalendar.get(1);
		int j = 1 + localCalendar.get(2);
		int k = localCalendar.get(5);
		int l = localCalendar.get(11);
		int i1 = localCalendar.get(12);
		int i2 = localCalendar.get(10);
		if (l >= 13) {
			if (i2 == 0)
				i2 = 12;
			if (i1 < 10)
				return i + "-" + j + "-" + k + "           " + "下午" + " " + i2
						+ ":" + "0" + i1;
			return i + "-" + j + "-" + k + "           " + "下午" + " " + i2
					+ ":" + i1;
		}
		if (i2 == 0)
			i2 = 12;
		if (i1 < 10)
			return i + "-" + j + "-" + k + "           " + "下午" + " " + i2
					+ ":" + "0" + i1;
		return i + "-" + j + "-" + k + "           " + "上午" + " " + i2 + ":"
				+ i1;
		
	}
}
