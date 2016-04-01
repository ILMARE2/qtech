package com.android.compus;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.compus.adapter.ChangeSqlite;
import com.android.compus.bean.Date;
import com.android.compus.bean.Notepad;
import com.android.compus.utils.SqliteHelper;
import com.android.compus.view.DrawLine;

/** 
 * ===============================
 * 作者: 静静茹她: 
 * 创建时间：2015年8月18日 下午3:34:18 
 * 版本号： 1.0 
 * 版权所有(C) 2015年8月18日
 * 描述： 编辑笔记
 *  ===============================
 */
public class EditNoteActivity extends NoteBaseActivity {
	private Button cancelButton;
	private String content;
	private Context context = this;
	private String date;
	private Date dateNow;
	private EditText editText;
	private String id;
	private Button sureButton;
	private TextView textView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		// TODO 自动生成的方法存根
		super.onCreate(paramBundle);
		setContentView(R.layout.showedit);
		this.textView = ((TextView) findViewById(R.id.editdate));
		this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.cancelButton = ((Button) findViewById(R.id.editbutton));
		this.sureButton = ((Button) findViewById(R.id.editbutton2));
		this.date = getIntent().getStringExtra("dateItem");
		this.content = getIntent().getStringExtra("contentItem");
		this.id = getIntent().getStringExtra("idItem");

		System.out.println("-----idItem-----id=" + id);
		this.editText.setSelection(this.editText.length());
		this.editText.setText(this.content);
		this.textView.setText(this.date);
		this.dateNow = new Date();
		this.date = this.dateNow.getDate();
		this.textView.setText(this.date);
		this.sureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(
						EditNoteActivity.this.context, null, null, 0)
						.getWritableDatabase();
				Notepad localNotepad = new Notepad();
				ChangeSqlite localChangeSqlite = new ChangeSqlite();
				String strContent = EditNoteActivity.this.editText.getText()
						.toString();
				if (strContent.equals("")) {
					Toast.makeText(EditNoteActivity.this.context, "内容为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle = strContent.length() > 11 ? " "
						+ strContent.substring(0, 11) : strContent;
				localNotepad.setContent(strContent);
				localNotepad.setTitle(strTitle);
				localNotepad.setdata(date);
				localNotepad.setid(id);
				System.out.println("-----id-----id=" + id);
				localChangeSqlite.update(localSqLiteDatabase, localNotepad);
				finish();
			}
		});
		this.cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
	}

}
