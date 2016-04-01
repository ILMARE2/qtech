package com.android.compus;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.compus.bean.ChatBean;
import com.android.compus.bean.VoiceBean;
import com.android.compus.bean.VoiceBean.WSBean;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class VoiceRobotActiviity extends BaseActivity {

	private ListView list_chat;
	private ArrayList<ChatBean> mChatList=new ArrayList<ChatBean>();
	private ChatAdapter mAdapter;
	
	private TextView tv_back;
	private TextView tv_title;
	
	private String[] mMMAnswers = new String[] { "Լ��?", "����!", "��Ҫ��Ҫ��!",
			"�������һ����!", "Ư����?" };

	private int[] mMMImageIDs = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3, R.drawable.p4 };

	
	@Override
	protected void initView() {
		
		
		setContentView(R.layout.activity_robot);
		list_chat=(ListView) findViewById(R.id.lv_chat);
		tv_back=(TextView) findViewById(R.id.tv_back);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("��������");
		
		//���ذ�ť���õ���¼�
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mAdapter = new ChatAdapter();
		//��listView����������
		list_chat.setAdapter(mAdapter);
		super.initView();
	}

	
	StringBuffer mTextBuffer = new StringBuffer();  //���ڱ�����������������ݣ�ƴ��һ�仰
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			//ʶ�𵽵Ľ�����ص�json���� Ҫ����
			//System.out.println(results.getResultString());
			 //System.out.println("isLast=" + isLast);

			String text = parseData(results.getResultString());
			mTextBuffer.append(text);

			if (isLast) {// �Ự����
				
				String finalText = mTextBuffer.toString();
				mTextBuffer = new StringBuffer();     // ����buffer
				
			    //System.out.println("���ս��:" + finalText);
				mChatList.add(new ChatBean(finalText, true, -1));

				String answer = "û����";
				int imageId = -1; 
				
				if (finalText.contains("���")) {
					answer = "��Һ�,������ĺ�!";
				} else if (finalText.contains("����˭")) {
					answer = "�������С����!";
				} else if (finalText.contains("�����ǵػ�")) {
					answer = "С����Ģ��";
					imageId = R.drawable.m;
				} else if (finalText.contains("��Ů")) {
					Random random = new Random();
					int i = random.nextInt(mMMAnswers.length);
					int j = random.nextInt(mMMImageIDs.length);
					answer = mMMAnswers[i];
					imageId = mMMImageIDs[j];
				}else if(finalText.contains("��ͼ")){
					answer="���ڴ򿪵�ͼ";
					Intent intent =new Intent(VoiceRobotActiviity.this,BaiduMapActivity.class);
					VoiceRobotActiviity.this.startActivity(intent);
				}else if(finalText.contains("�绰")){
					answer="���ڴ򿪲�����";
					Intent intent =new Intent();  
				    intent.setAction("android.intent.action.CALL_BUTTON");  
				    startActivity(intent);
				}else if(finalText.contains("����")){
					answer="���ڴ�ϵͳ����Ӧ��";
					Intent intent = new Intent(Intent.ACTION_VIEW);  
		            intent.setType("vnd.android-dir/mms-sms"); 
		            startActivity(intent);
				}else if(finalText.contains("��ϵ��")){
					answer="���ڴ��ֻ���ϵ��";
					  Intent intent = new Intent(); 
					    intent.setAction(Intent.ACTION_VIEW); 
					    intent.setData(Contacts.People.CONTENT_URI); 
					    startActivity(intent);
				}else if(finalText.contains("ͼ��")){
					answer="���ڴ�ͼ��";
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
				    intent.addCategory(Intent.CATEGORY_OPENABLE);
				    intent.setType("image/*");
				    startActivityForResult(intent, 0);  
				}else if(finalText.contains("���")){
					   answer="���ڴ����";
					   Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null); 
				       VoiceRobotActiviity.this.sendBroadcast(i);
				       long dateTaken = System.currentTimeMillis(); 
				        String name =  dateTaken+ ".jpg"; 
				        String  fileName = "/sdcard/" + name; 
				        ContentValues values = new ContentValues(); 
				        values.put(Images.Media.TITLE, fileName); 
				        values.put("_data", fileName); 
				        values.put(Images.Media.PICASA_ID, fileName); 
				        values.put(Images.Media.DISPLAY_NAME, fileName); 
				        values.put(Images.Media.DESCRIPTION, fileName); 
				        values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName); 
				        Uri photoUri = getContentResolver().insert( 
				        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
				        Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				        inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); 
				        startActivityForResult(inttPhoto, 10); 
				}else if(finalText.contains("����")&&finalText.contains("�Ƶ�")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "�Ƶ�");
					startActivity(intent);
				}else if(finalText.contains("����")&&finalText.contains("����")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "����");
					startActivity(intent);
				}else if(finalText.contains("����")&&finalText.contains("ktv")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "KTV");
					startActivity(intent);
				}else if(finalText.contains("����")&&finalText.contains("�̳�")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "�̳�");
					startActivity(intent);
				}else if(finalText.contains("����")&&finalText.contains("����վ")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "����վ");
					startActivity(intent);
				}else if(finalText.contains("����")&&finalText.contains("��԰")){
					answer="���������������Ժ󡣡�����";
					Intent intent=new Intent(getApplicationContext(),PoiSearchNearByActivity.class);
					intent.putExtra("search_things", "��԰");
					startActivity(intent);
				}
				
				mChatList.add(new ChatBean(answer, false, imageId));// ��ӻش�����
				mAdapter.notifyDataSetChanged();// ˢ��listview
				list_chat.setSelection(mChatList.size() - 1);// ��λ�����һ��

				read(answer);
			}
		}
		@Override
		public void onError(SpeechError arg0) {
		}
	};

	
	/**
	 * ��������
	 */
	public void read(String text) {
		SpeechSynthesizer mTts = SpeechSynthesizer
				.createSynthesizer(this, null);
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		mTts.setParameter(SpeechConstant.SPEED, "50");
		mTts.setParameter(SpeechConstant.VOLUME, "80");
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		mTts.startSpeaking(text, null);
	}
	
	
	/**
	 * ���˵����ť����¼�
	 */
	public void startListen(View view){
		RecognizerDialog iatDialog = new RecognizerDialog(this, null);
		// 2.������д������������ƴ�Ѷ��MSC API�ֲ�(Android)��SpeechConstant��
		iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		iatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		iatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
	}
	
	
	/**
	 * ListView ��������
	 * @author zhangchenggeng
	 *
	 */
	private class ChatAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mChatList.size();
		}

		@Override
		public ChatBean getItem(int position) {
			return mChatList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=View.inflate(getApplicationContext(), R.layout.list_chat_item, null);
				holder.tvAsk=(TextView) convertView.findViewById(R.id.tv_ask);
				holder.tvAnswer=(TextView) convertView.findViewById(R.id.tv_answer);
				holder.ivPic=(ImageView) convertView.findViewById(R.id.iv_pic);
				holder.llAnswer=(LinearLayout) convertView.findViewById(R.id.ll_answer);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			
			ChatBean item=getItem(position);
			
			if (item.isAsker) {// ��������
				holder.tvAsk.setVisibility(View.VISIBLE);
				holder.llAnswer.setVisibility(View.GONE);

				holder.tvAsk.setText(item.text);
			} else {
				holder.tvAsk.setVisibility(View.GONE);
				holder.llAnswer.setVisibility(View.VISIBLE);
				holder.tvAnswer.setText(item.text);
				
				if (item.imageId != -1) {// ��ͼƬ
					holder.ivPic.setVisibility(View.VISIBLE);
					holder.ivPic.setImageResource(item.imageId);
				} else {
					holder.ivPic.setVisibility(View.GONE);
				}
			}

			
			
			return convertView;
		}
		
	}
	
	
	static class ViewHolder{
		public TextView tvAsk;
		public TextView tvAnswer;
		public  LinearLayout llAnswer;
		public ImageView ivPic;
	}
	
	
	/**
	 * ������������
	 * @param resultString
	 */
	protected String parseData(String resultString) {
		Gson gson = new Gson();
		VoiceBean bean = gson.fromJson(resultString, VoiceBean.class);
		ArrayList<WSBean> ws = bean.ws;
		StringBuffer sb = new StringBuffer();
		for (WSBean wsBean : ws) {
			String text = wsBean.cw.get(0).w;
			sb.append(text);
		}
		return sb.toString();
	}
	
}
