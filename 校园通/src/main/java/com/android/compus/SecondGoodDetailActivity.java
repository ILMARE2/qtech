package com.android.compus;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 二手货物详情
 *  ===============================
 */
public class SecondGoodDetailActivity extends BaseActivity {


	@ViewInject(R.id.tv_secondgood_title)
	private TextView tv_secondgood_title;
	
	@ViewInject(R.id.iv_second_good)
	private ImageView iv_second_good;
	
	@ViewInject(R.id.tv_secondgood_time)
	private TextView  tv_secondgood_time;
	
	@ViewInject(R.id.tv_secondgood_price)
	private TextView  tv_secondgood_price;
	
	@ViewInject(R.id.tv_secondgood_describ)
	private TextView tv_secondgood_describ;
	
	@ViewInject(R.id.tv_secondgood_phone)
	private TextView tv_secondgood_phone;
	
	@ViewInject(R.id.tv_back)
	private TextView tv_back;

	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	
	private BitmapUtils bitmapUtils;
	
	@Override
	protected void initData() {

		//设置标题 时间 价格 描述 
		tv_secondgood_title.setText(getIntent().getStringExtra("title"));
		tv_secondgood_time.setText(getIntent().getStringExtra("time"));
		tv_secondgood_price.setText("¥"+getIntent().getStringExtra("price"));
		tv_secondgood_describ.setText(getIntent().getStringExtra("description"));
		tv_secondgood_phone.setText(getIntent().getStringExtra("phone"));
		
		//设置标题
		tv_title.setText("二手货物详情");
		
		//加载所需要的数据
        loadImage();
		super.initData();
	}

	@Override
	protected void setListener() {
		//返回按钮点击事件
		tv_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		super.setListener();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_second_goods);
		ViewUtils.inject(this);
		super.initView();
	}
	
	
	private void loadImage() {
		bitmapUtils = new BitmapUtils(getApplicationContext());
		String imageUrl=getIntent().getStringExtra("imageUrl");
		
		//方法一：利用BitmapUtils 加载图片
		if(!TextUtils.isEmpty(imageUrl)){
			iv_second_good.setVisibility(View.VISIBLE);
			bitmapUtils.display(iv_second_good, imageUrl);
		}
		
		//利用AsyncTask异步加载图片
//		if(imageUrl!=null&&!"".equals(imageUrl)){
//			new AsyncTask<String, Void, Bitmap>(){
//				protected void onPreExecute() {
//					super.onPreExecute();
//				}
//
//				protected void onPostExecute(Bitmap result) {
//					if(result!=null){
//						iv_second_good.setVisibility(View.VISIBLE);
//						iv_second_good.setImageBitmap(result);
//					}
//					super.onPostExecute(result);
//				}
//
//				@Override
//				protected Bitmap doInBackground(String... params) {
//					try {
//						Bitmap bitmap=NetUtil.getImage(params[0]);
//						return bitmap;
//					} catch (Exception e) {
//						
//						e.printStackTrace();
//						Toast.makeText(getApplicationContext(), "图片加载失败", 1).show();
//						return null;
//					}
//					
//				}
//				
//			}.execute(imageUrl);
//		}
		
		
	}

}
