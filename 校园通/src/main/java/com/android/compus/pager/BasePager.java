package com.android.compus.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.android.compus.R;


/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述：五个pager的基类
 * ===============================
 */
public abstract class BasePager {

	public Activity mActivity;
	public View rootview; // 根布局 包括标题栏跟中间的显示内容
	public TextView tv_title; // 标题
	public TextView tv_add; // 添加按钮
	public TextView tv_back; // 返回按钮
	public FrameLayout fl_content; // 中间的布局,不包括标题栏

	// 界面的五种状态
	public static final int STATE_UNKOWN = 0; // 未知状态
	public static final int STATE_LOADING = 1; // 加载状态
	public static final int STATE_ERROR = 2; // 错误状态
	public static final int STATE_EMPTY = 3; // 数据为空状态
	public static final int STATE_SUCCESS = 4; // 获取数据成功状态

	public int state = STATE_UNKOWN; // 记录当前状态

	// 四种界面。未知状态也用加载页面

	private View loadingView; // 加载中的界面
	private View errorView; // 错误界面
	private View emptyView; // 空界面
	private View successView; // 加载成功界面

	// 构造函数初始化
	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	// 初始化View
	public void initViews() {

		rootview = View.inflate(mActivity, R.layout.activity_basepager, null);
		tv_title = (TextView) rootview.findViewById(R.id.tv_title);
		tv_add = (TextView) rootview.findViewById(R.id.tv_add);
		tv_back = (TextView) rootview.findViewById(R.id.tv_back);
		fl_content = (FrameLayout) rootview.findViewById(R.id.fl_content);

		// 初始化四种界面
		initFourViews();

	}

	protected void initFourViews() {

		loadingView = createLoadingView(); // 创建了加载中的界面
		if (loadingView != null) {
			fl_content.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			fl_content.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		emptyView = createEmptyView(); // 加载空的界面
		if (emptyView != null) {
			fl_content.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		showPage();// 根据不同的状态显示不同的界面

	}

	// 确定具体显示那个界面
	private void showPage() {

		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}

	}

	// 请求服务器。根据服务器的不同状态，显示界面
	public void show() {	
		showPage();
	}

	// 创建了一个空的界面
	private View createEmptyView() {
		View view = View.inflate(mActivity, R.layout.loadpage_empty, null);
		return view;
	}

	// 创建了一个错误页面
	private View createErrorView() {
		View view = View.inflate(mActivity, R.layout.loadpage_error, null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		
		page_bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				show();
			}
		});

		return view;
	}

	// 创建了一个加载页面
	private View createLoadingView() {
		View view = View.inflate(mActivity, R.layout.loadpage_loading, null);
		return view;
	}

	// 初始化加载成功的数据  要求子类必须实现
	public abstract void initSuccessView();

}
