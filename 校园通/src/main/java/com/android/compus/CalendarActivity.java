package com.android.compus;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.android.compus.adapter.CalendarAdapter;

/**
 * ������ʾactivity

 */
public class CalendarActivity extends Activity implements OnGestureListener, OnClickListener {

	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0;      //ÿ�λ��������ӻ��ȥһ����,Ĭ��Ϊ0������ʾ��ǰ�£�
	private static int jumpYear = 0;       //������Խһ�꣬�����ӻ��߼�ȥһ��,Ĭ��Ϊ0(����ǰ��)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private Bundle bd=null;//���Ͳ���
	private Bundle bun=null;//���ղ���
	private String ruzhuTime;
	private String lidianTime;
	private String state="";
	private TextView btn_goback_to_today;   //���ؽ��찴ť
	private TextView left_img;
	private TextView right_img;
	
	private int gvFlag=0; //
	
	
	public CalendarActivity() {
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    	currentDate = sdf.format(date);  //��������
    	year_c = Integer.parseInt(currentDate.split("-")[0]);
    	month_c = Integer.parseInt(currentDate.split("-")[1]);
    	day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		//���ؽ��հ�ť
		btn_goback_to_today=(TextView) findViewById(R.id.btn_goback_to_today);
		btn_goback_to_today.setOnClickListener(this);
		//����������ť
		left_img=(TextView) findViewById(R.id.left_img);
		right_img=(TextView) findViewById(R.id.right_img);
		right_img.setOnClickListener(this);
		left_img.setOnClickListener(this);
		
		
		bd=new Bundle();//out
		bun=getIntent().getExtras();//in
		
		
		  if(bun!=null&&bun.getString("state").equals("ruzhu"))
          {
          	state=bun.getString("state");
          	System.out.println("%%%%%%"+state);
          }else if(bun!=null&&bun.getString("state").equals("lidian")){
          	
          	state=bun.getString("state");
          	System.out.println("|||||||||||"+state);
          }
		
		gestureDetector = new GestureDetector(this);

        calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
        addGridView();
        gridView.setAdapter(calV);
        
		topText = (TextView) findViewById(R.id.tv_month);
		addTextToTopTextView(topText);
	
	}
	
	
	//OnGestureListener
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
	
		if (e1.getX() - e2.getX() > 120) {
            goNextMonth(gvFlag);
	
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
            goPreMonth(gvFlag);

			return true;
		}
		return false;
	}

	private void goPreMonth(int gvFlag) {
		//���һ���
		addGridView();   //���һ��gridView
		jumpMonth--;     //��һ����
		
		calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		gridView.setAdapter(calV);
		gvFlag++;
		addTextToTopTextView(topText);
	}

	private void goNextMonth(int gvFlag) {
		//���󻬶�
		addGridView();   //���һ��gridView
		jumpMonth++;     //��һ����
		
		calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
		gvFlag++;
	}
	
	
	/**
	 * �����˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, menu.FIRST, menu.FIRST, "����");
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * ѡ��˵�
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
        case Menu.FIRST:
        	goBackToToday();

        	break;
        }
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return this.gestureDetector.onTouchEvent(event);
	}

	
	//OnGestureListener
	public boolean onDown(MotionEvent e) {
		return false;
	}
	//OnGestureListener
	public void onLongPress(MotionEvent e) {

	}
	//OnGestureListener
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
	}
	//OnGestureListener
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	//���ͷ������� �����µ���Ϣ
	public void addTextToTopTextView(TextView view){
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("��").append(
				calV.getShowMonth()).append("��").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	//���gridview
	private void addGridView() {
		
		gridView =(GridView)findViewById(R.id.gridview);

		gridView.setOnTouchListener(new OnTouchListener() {
            //��gridview�еĴ����¼��ش���gestureDetector
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return CalendarActivity.this.gestureDetector.onTouchEvent(event);
			}
		});           
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
            //gridView�е�ÿһ��item�ĵ���¼�
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				  //����κ�һ��item���õ����item������(�ų�����������յ�����(�������Ӧ))
				  int startPosition = calV.getStartPositon();
				  int endPosition = calV.getEndPosition();
				  if(startPosition <= position+7  && position <= endPosition-7){
					  String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //��һ�������
	                  String scheduleYear = calV.getShowYear();
	                  String scheduleMonth = calV.getShowMonth();
		              ruzhuTime=scheduleMonth+"��"+scheduleDay+"��";	                  
	                  lidianTime=scheduleMonth+"��"+scheduleDay+"��";       
	                Intent intent=new Intent();
	                if(state.equals("ruzhu"))
	                {
	                	
	                	bd.putString("ruzhu", ruzhuTime);
	                	System.out.println("ruzhuuuuuu"+bd.getString("ruzhu"));
	                }else if(state.equals("lidian")){
	                	
	                	bd.putString("lidian", lidianTime);
	                }

	                }
				  }
			
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_goback_to_today:
			goBackToToday();
			break;
		case R.id.left_img:
			goPreMonth(gvFlag);
			break;
		case R.id.right_img:
			goNextMonth(gvFlag);
			break;
		default:
			break;
		}
		
	}

	private void goBackToToday() {
		//��ת������
		int xMonth = jumpMonth;
		int xYear = jumpYear;
		int gvFlag =0;
		jumpMonth = 0;
		jumpYear = 0;
		addGridView();   //���һ��gridView
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
		gvFlag++;
	}
	
}