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
 *  作者: 静静茹她: 
 *  创建时间：2015年8月18日 下午3:34:18 
 *  版本号： 1.0
 * 	版权所有(C) 2015年8月18日
 *	描述： 添加二手货物 
 *===============================
 */
public class AddSecondGoodActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_add_image;
	private EditText et_name, et_price, et_phone, et_describe;
	private ImageView iv_good_image; // 显示图片的
	private String imagePath = ""; // 图片路径
	private TextView tv_back, tv_save, tv_title;

	// 显示数据
	@Override
	protected void initData() {
		tv_title.setText("添加商品");
		tv_save.setVisibility(View.VISIBLE);
		tv_save.setText("保存");

		boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
		if (isEdit) {
			et_price.setText(getIntent().getStringExtra("price"));
			et_describe.setText(getIntent().getStringExtra("description"));
			et_name.setText(getIntent().getStringExtra("title"));
			et_phone.setText(getIntent().getStringExtra("phone"));
			final String imageUrl = getIntent().getStringExtra("imageUrl");

			// 如果图片路径，且图片路径不为空 则加载图片
			if (imageUrl != null && !"".equals(imageUrl)) {
				new AsyncTask<Void, Void, Bitmap>() {
					// 图片加载之前的操作 是在主线程中的，可以开启一个进度条
					protected void onPreExecute() {
						super.onPreExecute();
					}

					// 加载完成后 取消掉进度条 并显示图片
					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							iv_good_image.setVisibility(View.VISIBLE);
							iv_good_image.setImageBitmap(result);
						}
						super.onPostExecute(result);
					}

					// 后台加载图片的操作，这个操作是在子线程中的
					protected Bitmap doInBackground(Void... params) {
						try {
							Bitmap bitmap = NetUtil.getImage(imageUrl);
							return bitmap;
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "加载图片失败！",
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
		// 处理按钮
		tv_back = (TextView) this.findViewById(R.id.tv_back);
		tv_save = (TextView) this.findViewById(R.id.tv_edit);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		btn_add_image = (Button) this.findViewById(R.id.btn_add_image);
		// 设置点击事件
		tv_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
		btn_add_image.setOnClickListener(this);

		// 处理editText
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_price = (EditText) this.findViewById(R.id.et_price);
		et_phone = (EditText) this.findViewById(R.id.et_phone);
		et_describe = (EditText) this.findViewById(R.id.et_describe);
		// 处理ImageView
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
		case R.id.btn_add_image: // 打开图库 让用户自己选择图片
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
				//好去到数据的Url
				imagePath = getAbsoluteImagePath(data.getData());
				loadImage(imagePath);
			}
		}
	}
	
	//加载大图片到内存
	private void loadImage(String imagePath) {
		// 1.得到屏幕的宽高信息
		WindowManager wm = getWindowManager();
		int screenWidth = wm.getDefaultDisplay().getWidth();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		System.out.println("屏幕宽高：" + screenWidth + "-" + screenHeight);

		// 2.得到图片的宽高。
		BitmapFactory.Options opts = new Options();// 解析位图的附加条件
		opts.inJustDecodeBounds = true;// 不去解析真实的位图，只是获取这个位图的头文件信息
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
		int bitmapWidth = opts.outWidth;
		int bitmapHeight = opts.outHeight;
		System.out.println("图片宽高： " + bitmapWidth + "-" + bitmapHeight);

		// 3.计算缩放比例
		int dx = bitmapWidth / screenWidth;
		int dy = bitmapHeight / screenHeight;
		int scale = 1;
		if (dx > dy && dy > 1) {
			System.out.println("按照水平方法缩放,缩放比例：" + dx);
			scale = dx;
		}

		if (dy > dx && dx > 1) {
			System.out.println("按照垂直方法缩放,缩放比例：" + dy);
			scale = dy;
		}
		
		// 4.缩放加载图片到内存。
		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false; // 真正的去解析这个位图。
		bitmap = BitmapFactory.decodeFile(imagePath, opts);
		iv_good_image.setImageBitmap(bitmap);
	}

	// 取到绝对路径
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
			Toast.makeText(getApplicationContext(), "商品描述不能为空", 1).show();
			return;
		}
		if (et_name.getText().toString() == null
				|| "".equals(et_name.getText().toString())) {
			Toast.makeText(getApplicationContext(), "商品名称不能为空", 1).show();
			return;
		}
		if (et_phone.getText().toString() == null
				|| "".equals(et_phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "联系电话不能为空", 1).show();
			return;
		}
		if (et_price.getText().toString() == null
				|| "".equals(et_price.getText().toString())) {
			Toast.makeText(getApplicationContext(), "商品价格不能为空", 1).show();
			return;
		}

		final SecondGood secondGood = new SecondGood();
		secondGood.setPrice(et_price.getText().toString());
		secondGood.setDescription(et_describe.getText().toString());
		secondGood.setTitle(et_name.getText().toString());
		secondGood.setPhone(et_phone.getText().toString());
		secondGood.setUserID(BmobUser.getCurrentUser(getApplicationContext())
				.getObjectId()); // 根据这一项判断是哪个用户发表的

		File file = new File(imagePath);
		if (file.exists()) { // 有图片。先上传图片，后保存信息
			BmobFile bmobFile = new BmobFile(file);
			secondGood.setImage(bmobFile);
			bmobFile.uploadblock(this, new UploadFileListener() {
				public void onSuccess() {
					secondGood.save(getApplicationContext(),
							new SaveListener() {
								@Override
								public void onSuccess() {
									Toast.makeText(getApplicationContext(),
											"保存二手货物信息成功", 1).show();
									setResult(RESULT_OK);
									finish();

								}

								@Override
								public void onFailure(int arg0, String arg1) {
									Toast.makeText(getApplicationContext(),
											"保存二手货物信息失败", 1).show();

								}
							});
				}

				public void onProgress(Integer arg0) {

				}

				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "上传图片失败！", 1)
							.show();
				}

			});
		} else { // 没有图片
			secondGood.save(getApplicationContext(), new SaveListener() {
				public void onSuccess() {
					Toast.makeText(getApplicationContext(), "保存二手货物信息成功", 1)
							.show();
					setResult(RESULT_OK);
					finish();

				}

				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "保存二手货物信息失败", 1)
							.show();

				}
			});
		}

	}

}