package com.android.compus.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetUtil {
	
	public static Bitmap getImage(String path)throws Exception{
		URL url=new URL(path);
		HttpURLConnection con= (HttpURLConnection) url.openConnection();
		InputStream is=con.getInputStream();
		return BitmapFactory.decodeStream(is);
	}
	
}

