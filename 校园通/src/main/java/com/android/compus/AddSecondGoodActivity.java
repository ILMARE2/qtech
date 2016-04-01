package com.android.compus;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.android.compus.bean.SecondGood;
import com.android.compus.utils.NetUtil;

/**
 * ===============================
 *  ����: ��������: 
 *  ����ʱ�䣺2015��8��18�� ����3:34:18 
 *  �汾�ţ� 1.0
 * 	��Ȩ����(C) 2015��8��18��
 *	������ ��Ӷ��ֻ��� 
 *===============================
 */
public class AddSecondGoodActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_add_image;
	private EditText et_name, et_price, et_phone, et_describe;
	private ImageView iv_good_image; // ��ʾͼƬ��
	private String imagePath = ""; // ͼƬ·��
	private TextView tv_back, tv_save, tv_title;

	// ��ʾ����
	@Override
	protected void initData() {
		tv_title.setText("�����Ʒ");
		tv_save.setVisibility(View.VISIBLE);
		tv_save.setText("����");

		boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
		if (isEdit) {
			et_price.setText(getIntent().getStringExtra("price"));
			et_describe.setText(getIntent().getStringExtra("description"));
			et_name.setText(getIntent().getStringExtra("title"));
			et_phone.setText(getIntent().getStringExtra("phone"));
			final String imageUrl = getIntent().getStringExtra("imageUrl");

			// ���ͼƬ·������ͼƬ·����Ϊ�� �����ͼƬ
			if (imageUrl != null && !"".equals(imageUrl)) {
				new AsyncTask<Void, Void, Bitmap>() {
					// ͼƬ����֮ǰ�Ĳ��� �������߳��еģ����Կ���һ��������
					protected void onPreExecute() {
						super.onPreExecute();
					}

					// ������ɺ� ȡ���������� ����ʾͼƬ
					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							iv_good_image.setVisibility(View.VISIBLE);
							iv_good_image.setImageBitmap(result);
						}
						super.onPostExecute(result);
					}

					// ��̨����ͼƬ�Ĳ�������������������߳��е�
					protected Bitmap doInBackground(Void... params) {
						try {
							Bitmap bitmap = NetUtil.getImage(imageUrl);
							return bitmap;
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "����ͼƬʧ�ܣ�",
									1).show();
							return null;
						}
					}

				}.execute();
			}
		}
		super.initData();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_add_second_good);
		// ����ť
		tv_back = (TextView) this.findViewById(R.id.tv_back);
		tv_save = (TextView) this.findViewById(R.id.tv_edit);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		btn_add_image = (Button) this.findViewById(R.id.btn_add_image);
		// ���õ���¼�
		tv_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
		btn_add_image.setOnClickListener(this);

		// ����editText
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_price = (EditText) this.findViewById(R.id.et_price);
		et_phone = (EditText) this.findViewById(R.id.et_phone);
		et_describe = (EditText) this.findViewById(R.id.et_describe);
		// ����ImageView
		iv_good_image = (ImageView) this.findViewById(R.id.iv_good_image);

		super.initView();
	}

	private void setView() {

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.tv_edit:
			saveSecondGood();
			break;
		case R.id.btn_add_image: // ��ͼ�� ���û��Լ�ѡ��ͼƬ
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, 2);
			break;

		default:
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			if (data != null) {
				//��ȥ�����ݵ�Url
				imagePath = getAbsoluteImagePath(data.getData());
				loadImage(imagePath);
			}
		}
	}
	
	//���ش�ͼƬ���ڴ�
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
		iv_good_image.setImageBitmap(bitmap);
	}

	// ȡ������·��
	protected String getAbsoluteImagePath(Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, // Which columns to return
				null,         // WHERE clause; which rows to return (all rows)
				null,        // WHERE clause selection arguments (none)
				null);        // Order-by clause (ascending by name)

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void saveSecondGood() {
		if (et_describe.getText().toString() == null
				|| "".equals(et_describe.getText().toString())) {
			Toast.makeText(getApplicationContext(), "��Ʒ��������Ϊ��", 1).show();
			return;
		}
		if (et_name.getText().toString() == null
				|| "".equals(et_name.getText().toString())) {
			Toast.makeText(getApplicationContext(), "��Ʒ���Ʋ���Ϊ��", 1).show();
			return;
		}
		if (et_phone.getText().toString() == null
				|| "".equals(et_phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "��ϵ�绰����Ϊ��", 1).show();
			return;
		}
		if (et_price.getText().toString() == null
				|| "".equals(et_price.getText().toString())) {
			Toast.makeText(getApplicationContext(), "��Ʒ�۸���Ϊ��", 1).show();
			return;
		}

		final SecondGood secondGood = new SecondGood();
		secondGood.setPrice(et_price.getText().toString());
		secondGood.setDescription(et_describe.getText().toString());
		secondGood.setTitle(et_name.getText().toString());
		secondGood.setPhone(et_phone.getText().toString());
		secondGood.setUserID(BmobUser.getCurrentUser(getApplicationContext())
				.getObjectId()); // ������һ���ж����ĸ��û������

		File file = new File(imagePath);
		if (file.exists()) { // ��ͼƬ�����ϴ�ͼƬ���󱣴���Ϣ
			BmobFile bmobFile = new BmobFile(file);
			secondGood.setImage(bmobFile);
			bmobFile.uploadblock(this, new UploadFileListener() {
				public void onSuccess() {
					secondGood.save(getApplicationContext(),
							new SaveListener() {
								@Override
								public void onSuccess() {
									Toast.makeText(getApplicationContext(),
											"������ֻ�����Ϣ�ɹ�", 1).show();
									setResult(RESULT_OK);
									finish();

								}

								@Override
								public void onFailure(int arg0, String arg1) {
									Toast.makeText(getApplicationContext(),
											"������ֻ�����Ϣʧ��", 1).show();

								}
							});
				}

				public void onProgress(Integer arg0) {

				}

				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "�ϴ�ͼƬʧ�ܣ�", 1)
							.show();
				}

			});
		} else { // û��ͼƬ
			secondGood.save(getApplicationContext(), new SaveListener() {
				public void onSuccess() {
					Toast.makeText(getApplicationContext(), "������ֻ�����Ϣ�ɹ�", 1)
							.show();
					setResult(RESULT_OK);
					finish();

				}

				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "������ֻ�����Ϣʧ��", 1)
							.show();

				}
			});
		}

	}

}