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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
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

    Button btn_return,btn_other;   //���ذ�ť ������ť

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather_screen);
		
		initView();
		
		progressDialog = ProgressDialog.show(WeatherScreen.this,null, "������ѯ��...",true, true);
		//cityName="����";
		cityName=SharePreferencesUtils.getString(getApplicationContext(), "weather_city", "����");
		
		QueryAsyncTask asyncTask = new QueryAsyncTask();
		asyncTask.execute("");
				
	}
	

	/**
	 * 
	 * ��������initView 
	 * ���ܣ���ʼ���ؼ�
	 * ������
	 * ����ʱ�䣺2012-7-17
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
							DataUtil.Alert(WeatherScreen.this,"����������Ϣ");
						}
					}else{
						DataUtil.Alert(WeatherScreen.this,"����������Ϣ");
					}
				}else{
					DataUtil.Alert(WeatherScreen.this,"����������Ϣ");
				}
			}else{
				DataUtil.Alert(WeatherScreen.this,"����������Ϣ");
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
	 * ��������todayParse 
	 * ���ܣ���������
	 * ������
	 * @param weather
	 * �����ˣ�huanghsh  
	 * ����ʱ�䣺2012-10-17
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
					wd=tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			
			tv_attr1.setText("���£�"+wd);
			tv_attr2.setText("���������"+tq);
			tv_attr3.setText("����"+fx);
			ima.setImageResource(imageResoId(tq));
			
		}
	}
	
	/**
	 * 
	 * ��������tommrowParse 
	 * ���ܣ���������
	 * ������
	 * @param weather
	 * �����ˣ�huanghsh  
	 * ����ʱ�䣺2012-10-17
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
					wd=tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			tv_date1.setText("����    "+tq);
			tv_wd1.setText(wd);
			ima1.setImageResource(imageResoId(tq));
			
			
		}
	}
	
	/**
	 * 
	 * ��������thirddayParse 
	 * ���ܣ���ȡ��������
	 * ������
	 * @param weather
	 * �����ˣ�huanghsh  
	 * ����ʱ�䣺2012-10-17
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
					wd=tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("t2:")!=-1){
					wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"��";
				}else if(tempArr[i].indexOf("d1:")!=-1){
					fx=tempArr[i].substring(3,tempArr[i].length());
				}else if(tempArr[i].indexOf("s1:")!=-1){
					tq=tempArr[i].substring(4,tempArr[i].length());
				}
			}
			
			tv_date2.setText("����    "+tq);
			tv_wd2.setText(wd);
			ima2.setImageResource(imageResoId(tq));
			
		}
	}
	/**
	 * 
	 * ��������imageResoId 
	 * ���ܣ���ȡͼƬ
	 * ������
	 * @param weather
	 * @return
	 * �����ˣ�huanghsh  
	 * ����ʱ�䣺2012-10-17
	 */
	private int imageResoId(String weather){
		int resoid=R.drawable.s_2;
		if(weather.indexOf("����")!=-1||weather.indexOf("��")!=-1){//����ת�磬������ͬ indexOf:�����ִ�
            resoid=R.drawable.s_1;}
        else if(weather.indexOf("����")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_2;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_3;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("��")!=-1){resoid=R.drawable.s_13;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_2;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("С��")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_4;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_6;}
        else if(weather.indexOf("������")!=-1){resoid=R.drawable.s_7;}
        else if(weather.indexOf("Сѩ")!=-1){resoid=R.drawable.s_8;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_9;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("��ɳ")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("ɳ��")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("��")!=-1){resoid=R.drawable.s_12;}
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
	 * ��������showOtherCity 
	 * ���ܣ�����������������
	 * ������
	 * �����ˣ�huanghsh  
	 * ����ʱ�䣺2012-10-17
	 */
	private void showOtherCity(){
		LayoutInflater inflater = getLayoutInflater();
		   View layout = inflater.inflate(R.layout.weather_other_city,(ViewGroup) findViewById(R.id.ws_dialog));
		    dialogCity = (EditText)layout.findViewById(R.id.ws_city_name);
		   new AlertDialog.Builder(this).setTitle("�������������").setView(layout)
		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		        	 cityName=dialogCity.getText().toString();
		        	 SharePreferencesUtils.setString(getApplicationContext(), "weather_city", cityName);
		        	 if(cityName!=null&&cityName.length()>0){
		        		 progressDialog = ProgressDialog.show(WeatherScreen.this,null, "������ѯ��...",true, true);
						 QueryAsyncTask asyncTask = new QueryAsyncTask();
						 asyncTask.execute("");
		        	 }
		           }
		       })
		     .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       }).show();
	}
}
