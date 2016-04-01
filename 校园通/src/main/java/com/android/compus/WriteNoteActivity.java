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
 * ����: ��������: 
 * ����ʱ�䣺2015��8��18�� ����3:34:18 
 * �汾�ţ� 1.0 
 * ��Ȩ����(C) 2015��8��18��
 * ������ ���������
 *  ===============================
 */
public class WriteNoteActivity extends NoteBaseActivity {
	  private Button cancelButton;
	  private Context context = this;
	  private String date;
	  private EditText editText;
	  private Date getDate;
	  private Button sureButton;
	  private TextView textView;

	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.writedown);
	    this.textView = ((TextView)findViewById(R.id.writedate));
	    this.editText = ((DrawLine)findViewById(R.id.edittext));
	    this.cancelButton = ((Button)findViewById(R.id.button));
	    this.sureButton = ((Button)findViewById(R.id.button2));
	    this.getDate = new Date();
	    this.date = this.getDate.getDate();
	    this.textView.setText(this.date);
	    this.sureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				SQLiteDatabase localSqLiteDatabase = new SqliteHelper(WriteNoteActivity.this.context, null, null, 0).getWritableDatabase();
				Notepad localNotepad = new Notepad();
				ChangeSqlite localChangeSqlite = new ChangeSqlite();
				String strContent = WriteNoteActivity.this.editText.getText().toString();
				if (strContent.equals("")) {
					Toast.makeText(WriteNoteActivity.this.context, "����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
				localNotepad.setContent(strContent);
				localNotepad.setTitle(strTitle);
				localNotepad.setdata(date);
				localChangeSqlite.add(localSqLiteDatabase, localNotepad);
				finish();
			}
		});
	    this.cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				finish();
			}
		});
	  }
}


























