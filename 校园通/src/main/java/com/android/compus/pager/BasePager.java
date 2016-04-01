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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ���������pager�Ļ���
 * ===============================
 */
public abstract class BasePager {

	public Activity mActivity;
	public View rootview; // ������ �������������м����ʾ����
	public TextView tv_title; // ����
	public TextView tv_add; // ��Ӱ�ť
	public TextView tv_back; // ���ذ�ť
	public FrameLayout fl_content; // �м�Ĳ���,������������

	// ���������״̬
	public static final int STATE_UNKOWN = 0; // δ֪״̬
	public static final int STATE_LOADING = 1; // ����״̬
	public static final int STATE_ERROR = 2; // ����״̬
	public static final int STATE_EMPTY = 3; // ����Ϊ��״̬
	public static final int STATE_SUCCESS = 4; // ��ȡ���ݳɹ�״̬

	public int state = STATE_UNKOWN; // ��¼��ǰ״̬

	// ���ֽ��档δ֪״̬Ҳ�ü���ҳ��

	private View loadingView; // �����еĽ���
	private View errorView; // �������
	private View emptyView; // �ս���
	private View successView; // ���سɹ�����

	// ���캯����ʼ��
	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	// ��ʼ��View
	public void initViews() {

		rootview = View.inflate(mActivity, R.layout.activity_basepager, null);
		tv_title = (TextView) rootview.findViewById(R.id.tv_title);
		tv_add = (TextView) rootview.findViewById(R.id.tv_add);
		tv_back = (TextView) rootview.findViewById(R.id.tv_back);
		fl_content = (FrameLayout) rootview.findViewById(R.id.fl_content);

		// ��ʼ�����ֽ���
		initFourViews();

	}

	protected void initFourViews() {

		loadingView = createLoadingView(); // �����˼����еĽ���
		if (loadingView != null) {
			fl_content.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		errorView = createErrorView(); // ���ش������
		if (errorView != null) {
			fl_content.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		emptyView = createEmptyView(); // ���ؿյĽ���
		if (emptyView != null) {
			fl_content.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		showPage();// ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ���

	}

	// ȷ��������ʾ�Ǹ�����
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

	// ��������������ݷ������Ĳ�ͬ״̬����ʾ����
	public void show() {	
		showPage();
	}

	// ������һ���յĽ���
	private View createEmptyView() {
		View view = View.inflate(mActivity, R.layout.loadpage_empty, null);
		return view;
	}

	// ������һ������ҳ��
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

	// ������һ������ҳ��
	private View createLoadingView() {
		View view = View.inflate(mActivity, R.layout.loadpage_loading, null);
		return view;
	}

	// ��ʼ�����سɹ�������  Ҫ���������ʵ��
	public abstract void initSuccessView();

}
