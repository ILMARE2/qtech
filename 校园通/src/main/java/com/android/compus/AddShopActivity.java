package com.android.compus;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.android.compus.bean.Shop;

/** 
 * ===============================
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ��ӵ���
 *  ===============================
 */
public class AddShopActivity extends BaseActivity implements OnClickListener {
	private EditText et_name,et_location,et_phone,et_describe,et_sale;
	private ImageView iv_shop_image;
	private Button btn_add_image;
	private TextView tv_back,tv_add,tv_title;
	private String imagePath="";
	private String type;
	@Override
	protected void initData() {
		tv_title.setText("��ӵ���");
		tv_add.setVisibility(View.VISIBLE);
		tv_add.setText("����");
		type=getIntent().getStringExtra("type");
		
		super.initData();
	}
	@Override
	protected void setListener() {
		btn_add_image.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		tv_add.setOnClickListener(this);
		super.setListener();
	}
	@Override
	protected void initView() {
		setContentView(R.layout.activity_add_shop);
		et_name=(EditText) this.findViewById(R.id.et_name);
		et_location=(EditText) this.findViewById(R.id.et_location);
		et_phone=(EditText) this.findViewById(R.id.et_phone);
		et_describe=(EditText) this.findViewById(R.id.et_describe);
		et_sale=(EditText) this.findViewById(R.id.et_sale);
		iv_shop_image=(ImageView) this.findViewById(R.id.iv_shop_image);
		btn_add_image= (Button) this.findViewById(R.id.btn_add_image);
		tv_back= (TextView) this.findViewById(R.id.tv_back);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_add= (TextView) this.findViewById(R.id.tv_edit);
		super.initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_image:
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, 2);
			break;
		case R.id.tv_edit:
			saveShop(imagePath);
			break;
		case R.id.tv_back:
			finish();
			break;
		default:
			break;
		}
	}
	
	private void saveShop(String imagePath) {
		if (et_name.getText().toString() == null
				|| "".equals(et_name.getText().toString())) {
			Toast.makeText(getApplicationContext(), "�������Ʋ���Ϊ��", 1).show();
			return ;
		}
		if (et_location.getText().toString() == null
				|| "".equals(et_location.getText().toString())) {
			Toast.makeText(getApplicationContext(), "���̵ص㲻��Ϊ��", 1).show();
			return ;
		}
		if (et_phone.getText().toString() == null
				|| "".equals(et_phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "��ϵ�绰����Ϊ��", 1).show();
			return ;
		}
		if (et_describe.getText().toString() == null
				|| "".equals(et_describe.getText().toString())) {
			Toast.makeText(getApplicationContext(), "������������Ϊ��", 1).show();
			return ;
		}
		
		final Shop shop =new Shop();
		shop.setInfo(et_describe.getText().toString());
		shop.setName(et_name.getText().toString());
		shop.setPhone(et_phone.getText().toString());
		shop.setLocation(et_location.getText().toString());
		shop.setSale(et_sale.getText().toString());
		shop.setType(type);
		File file = new File(imagePath);
		if(file.exists()){
			BmobFile bmobFile = new BmobFile(file);
			shop.setPicShop(bmobFile);
			bmobFile.uploadblock(getApplicationContext(), new UploadFileListener() {
				
				@Override
				public void onSuccess() {
					shop.save(getApplicationContext(), new SaveListener() {
						
						public void onSuccess() {
							Toast.makeText(getApplicationContext(), "������̳ɹ�", 1).show();
							setResult(RESULT_OK);
							finish();
						}
						
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(getApplicationContext(), "�������ʧ��", 1).show();
							
						}
					});
					
				}
				
				@Override
				public void onProgress(Integer arg0) {
					
					
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "�ϴ�ͼƬʧ�ܣ�", 1).show();
					
				}
			});
		}else{
			shop.save(getApplicationContext(), new SaveListener() {
				
				@Override
				public void onSuccess() {
					Toast.makeText(getApplicationContext(), "������̳ɹ�", 1).show();
					setResult(RESULT_OK);
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "�������ʧ��", 1).show();
					
				}
			});
		}
		
	
		
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			if (data != null) {
				imagePath = getAbsoluteImagePath(data.getData());
				loadImage(imagePath);
			}
		}
	}

	
	
	private void loadImage(String imagePath) {
		// 1.�õ���Ļ�Ŀ����Ϣ
		WindowManager wm = getWindowManager();
		int screenWidth = wm.getDefaultDisplay().getWidth();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		System.out.println("��Ļ��ߣ�" + screenWidth + "-" + screenHeight);

		// 2.�õ�ͼƬ�Ŀ�ߡ�
		BitmapFactory.Options opts = new Options();// ����λͼ�ĸ�������
		opts.inJustDecodeBounds = true;// ��ȥ������ʵ��λͼ��ֻ�ǻ�ȡ���λͼ��ͷ�ļ���Ϣ
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
		int bitmapWidth = opts.outWidth;
		int bitmapHeight = opts.outHeight;
		System.out.println("ͼƬ��ߣ� " + bitmapWidth + "-" + bitmapHeight);

		// 3.�������ű���
		int dx = bitmapWidth / screenWidth;
		int dy = bitmapHeight / screenHeight;
		int scale = 1;
		if (dx > dy && dy > 1) {
			System.out.println("����ˮƽ��������,���ű�����" + dx);
			scale = dx;
		}

		if (dy > dx && dx > 1) {
			System.out.println("���մ�ֱ��������,���ű�����" + dy);
			scale = dy;
		}
		// 4.���ż���ͼƬ���ڴ档
		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false; // ������ȥ�������λͼ��
		bitmap = BitmapFactory.decodeFile(imagePath, opts);
		iv_shop_image.setImageBitmap(bitmap);
	}

	// ȡ������·��
	protected String getAbsoluteImagePath(Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	
}
