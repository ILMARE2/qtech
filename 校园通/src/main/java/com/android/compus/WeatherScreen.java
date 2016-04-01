package com.android.compus;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.compus.utils.DataUtil;
import com.android.compus.utils.HttpService;
import com.android.compus.utils.SharePreferencesUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;



/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 聊天机器人
 *  ===============================
 */
public class WeatherScreen extends Activity implements OnClickListener {

	String npCityId="";
    EditText dialogCity;
    String provinceName, cityName;
    boolean flag =true;
    ProgressDialog progressDialog;
    LinearLayout ll_yes,ll_no;
    TextView tv_city,tv_today,tv_attr1,tv_attr2,tv_attr3,tv_noresult;
    TextView tv_date1,tv_date2,tv_wd1,tv_wd2;
    ImageView ima,ima1,ima2;

    Button btn_return,btn_other;   //返回按钮 其他按钮

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather_screen);
		
		initView();
		
		progressDialog = ProgressDialog.show(WeatherScreen.this,null, "天气查询中...",true, true);
		//cityName="北京";
		cityName=SharePreferencesUtils.getString(getApplicationContext(), "weather_city", "北京");
		
		QueryAsyncTask asyncTask = new QueryAsyncTask();
		asyncTask.execute("");
				
	}
	

	/**
	 * 
	 * 方法名：initView 
	 * 功能：初始化控件
	 * 参数：
	 * 创建时间：2012-7-17
	 */
	private void initView(){
		 ll_yes = (LinearLayout)this.findViewById(R.id.ws2_ll_yes);
		 ll_no= (LinearLayout)this.findViewById(R.id.ws2_ll_no);
		 
		 tv_city= (TextView)this.findViewById(R.id.ws2_tv_city);
		 ima= (ImageView)this.findViewById(R.id.ws2_iv_image);
		 tv_attr1= (TextView)this.findViewById(R.id.ws2_tv_attr1);
		 tv_attr2= (TextView)this.findViewById(R.id.ws2_tv_attr2);
		 tv_attr3= (TextView)this.findViewById(R.id.ws2_tv_attr3);
		 
		 tv_noresult = (TextView)this.findViewById(R.id.ws2_tv_noresult);
		 
		 tv_date1= (TextView)this.findViewById(R.id.ws2_tv_1_date);
		 tv_date2= (TextView)this.findViewById(R.id.ws2_tv_2_date);
		 tv_wd1= (TextView)this.findViewById(R.id.ws2_tv_1_wd);
		 tv_wd2= (TextView)this.findViewById(R.id.ws2_tv_2_wd);
		 
		 ima1= (ImageView)this.findViewById(R.id.ws2_iv_1_image);
		 ima2= (ImageView)this.findViewById(R.id.ws2_iv_2_image);
		 
		 btn_return= (Button)this.findViewById(R.id.ws2_btn_return);
		 btn_return.setOnClickListener(this);
		 btn_other= (Button)this.findViewById(R.id.ws2_btn_submit);
		 btn_other.setOnClickListener(this);
	}
	
	
	
	private class QueryAsyncTask extends AsyncTask {
		@Override
		protected void onPostExecute(Object result) {
			progressDialog.dismiss();
			if(result!=null){
				String weatherResult = (String)result;
				if(weatherResult.split(";").length>1){
					String a  = weatherResult.split(";")[1];
					if(a.split("=").length>1){
						String b = a.split("=")[1];
						String c = b.substring(1,b.length()-1);
						String[] resultArr = c.split("\\}");
						if(resultArr.length>1){
								todayParse(resultArr[0]);
								tommrowParse(resultArr[1]);
								thirddayParse(resultArr[2]);
								ll_yes.setVisibility(View.VISIBLE);
								tv_city.setText(cityName);
						}else{
							DataUtil.Alert(WeatherScreen.this,"查无天气信息");
						}
					}else{
						DataUtil.Alert(WeatherScreen.this,"查无天气信息");
					}
				}else{
					DataUtil.Alert(WeatherScreen.this,"查无天气信息");
				}
			}else{
				DataUtil.Alert(WeatherScreen.this,"查无天气信息");
			}
			super.onPostExecute(result);			
		}
			
		@Override
		protected Object doInBackground(Object... params) {
			return HttpService.getWeather(cityName);
		}
		
		
	}

	/**
	 * 
	 * 方法名：todayParse 
	 * 功能：今天天气
	 * 参数：
	 * @param weather
	 * 创建人：huanghsh  
	 * 创建时间：2012-10-17
	 */
	private void todayParse(String weather){
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd="";
		String tq="";
		String fx="";
		if(tempArr.length>0){
			for(int i=0;i<tempArr.length;i++){
				if(tempArr[i].indexOf("t1:")!=-1){
					wd=tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			
			tv_attr1.setText("气温："+wd);
			tv_attr2.setText("天气情况："+tq);
			tv_attr3.setText("风向："+fx);
			ima.setImageResource(imageResoId(tq));
			
		}
	}
	
	/**
	 * 
	 * 方法名：tommrowParse 
	 * 功能：明天天气
	 * 参数：
	 * @param weather
	 * 创建人：huanghsh  
	 * 创建时间：2012-10-17
	 */
	private void tommrowParse(String weather){
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd="";
		String tq="";
		String fx="";
		if(tempArr.length>0){
			for(int i=0;i<tempArr.length;i++){
				if(tempArr[i].indexOf("t1:")!=-1){
					wd=tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			tv_date1.setText("明天    "+tq);
			tv_wd1.setText(wd);
			ima1.setImageResource(imageResoId(tq));
			
			
		}
	}
	
	/**
	 * 
	 * 方法名：thirddayParse 
	 * 功能：获取后天天气
	 * 参数：
	 * @param weather
	 * 创建人：huanghsh  
	 * 创建时间：2012-10-17
	 */
	private void thirddayParse(String weather){
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd="";
		String tq="";
		String fx="";
		if(tempArr.length>0){
			for(int i=0;i<tempArr.length;i++){
				if(tempArr[i].indexOf("t1:")!=-1){
					wd=tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"℃";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			
			tv_date2.setText("后天    "+tq);
			tv_wd2.setText(wd);
			ima2.setImageResource(imageResoId(tq));
			
		}
	}
	/**
	 * 
	 * 方法名：imageResoId 
	 * 功能：获取图片
	 * 参数：
	 * @param weather
	 * @return
	 * 创建人：huanghsh  
	 * 创建时间：2012-10-17
	 */
	private int imageResoId(String weather){
		int resoid=R.drawable.s_2;
		if(weather.indexOf("多云")!=-1||weather.indexOf("晴")!=-1){//多云转晴，以下类同 indexOf:包含字串
            resoid=R.drawable.s_1;}
        else if(weather.indexOf("多云")!=-1&&weather.indexOf("阴")!=-1){
            resoid=R.drawable.s_2;}
        else if(weather.indexOf("阴")!=-1&&weather.indexOf("雨")!=-1){
            resoid=R.drawable.s_3;}
        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雨")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("晴")!=-1&&weather.indexOf("雾")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("晴")!=-1){resoid=R.drawable.s_13;}
        else if(weather.indexOf("多云")!=-1){resoid=R.drawable.s_2;}
        else if(weather.indexOf("阵雨")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("小雨")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("中雨")!=-1){resoid=R.drawable.s_4;}
        else if(weather.indexOf("大雨")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("暴雨")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("冰雹")!=-1){resoid=R.drawable.s_6;}
        else if(weather.indexOf("雷阵雨")!=-1){resoid=R.drawable.s_7;}
        else if(weather.indexOf("小雪")!=-1){resoid=R.drawable.s_8;}
        else if(weather.indexOf("中雪")!=-1){resoid=R.drawable.s_9;}
        else if(weather.indexOf("大雪")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("暴雪")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("扬沙")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("沙尘")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("雾")!=-1){resoid=R.drawable.s_12;}
		return resoid;
	}
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ws2_btn_return:
			finish();
			break;
		case R.id.ws2_btn_submit:
			showOtherCity();
			break;
		}
	}
	
	/**
	 * 
	 * 方法名：showOtherCity 
	 * 功能：输入其他城市名称
	 * 参数：
	 * 创建人：huanghsh  
	 * 创建时间：2012-10-17
	 */
	private void showOtherCity(){
		LayoutInflater inflater = getLayoutInflater();
		   View layout = inflater.inflate(R.layout.weather_other_city,(ViewGroup) findViewById(R.id.ws_dialog));
		    dialogCity = (EditText)layout.findViewById(R.id.ws_city_name);
		   new AlertDialog.Builder(this).setTitle("请输入城市名称").setView(layout)
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		        	 cityName=dialogCity.getText().toString();
		        	 SharePreferencesUtils.setString(getApplicationContext(), "weather_city", cityName);
		        	 if(cityName!=null&&cityName.length()>0){
		        		 progressDialog = ProgressDialog.show(WeatherScreen.this,null, "天气查询中...",true, true);
						 QueryAsyncTask asyncTask = new QueryAsyncTask();
						 asyncTask.execute("");
		        	 }
		           }
		       })
		     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       }).show();
	}
}
