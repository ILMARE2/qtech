package com.android.compus.pay;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCQRCodeResult;

import com.android.compus.R;

/**
 * ����չʾ������ɶ�ά��֧��
 */
public class GenQRCodeActivity extends Activity {
    private static final String Tag = "GenQRCodeActivity";
    private ProgressDialog loadingDialog;

    Button btnReqWXQRCode;
    Button btnReqALIQRCode;
    Button btnReqALIOfflineQRCode;

    private ImageView wxQRImg;
    private ImageView aliOfflineQRImg;

    private Handler mHandler;

    private Bitmap wxQRBitmap;
    private String aliQRHtml;
    private String aliQRURL;
    private Bitmap aliOfflineQRBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qrcode);

        loadingDialog = new ProgressDialog(GenQRCodeActivity.this);
        loadingDialog.setMessage("�������������, ���Ժ�...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(true);

        btnReqWXQRCode = (Button) findViewById(R.id.btnReqWXQRCode);
        wxQRImg = (ImageView) findViewById(R.id.wxQRImg);

        btnReqALIQRCode = (Button) findViewById(R.id.btnReqALIQRCode);

        btnReqALIOfflineQRCode = (Button) findViewById(R.id.btnReqALIOfflineQRCode);
        aliOfflineQRImg = (ImageView) findViewById(R.id.aliOfflineQRImg);

        // Defines a Handler object that's attached to the UI thread.
        // ͨ��Handler.Callback()�������ڴ�й©����
        mHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        wxQRImg.setImageBitmap(wxQRBitmap);
                        break;
                    case 2:
                        //�������µ�activity��ʾali��Ƕ��ά��
                        Intent intent = new Intent(GenQRCodeActivity.this, ALIQRCodeActivity.class);
                        intent.putExtra("aliQRURL", aliQRURL);
                        intent.putExtra("aliQRHtml", aliQRHtml);
                        startActivity(intent);
                        break;
                    case 3:
                        aliOfflineQRImg.setImageBitmap(aliOfflineQRBitmap);
                }

                return true;
            }
        });

        btnReqWXQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();

                Map<String, String> mapOptional = new HashMap<String, String>();

                mapOptional.put("testkey1", "����valueֵ1");
                BCPay.getInstance(GenQRCodeActivity.this).reqWXQRCodeAsync("΢�Ŷ�ά��֧������", //��Ʒ����
                        1,                          //�ܽ��, �Է�Ϊ��λ, ������������
                        UUID.randomUUID().toString().replace("-", ""),          //��ˮ��
                        mapOptional,            //��չ����
                        true,                   //�Ƿ����ɶ�ά���bitmap,
                                                //���Ϊfalse�������и���getQrCodeRawContent���صĽ��
                                                //ʹ��BCPay.generateBitmap��������֧����ά��
                                                //��Ҳ����ʹ���Լ���Ϥ�Ķ�ά�����ɹ���
                        300,                   //��ά��ĳߴ�, ��pxΪ��λ, ���Ϊnull��Ĭ��Ϊ360
                        new BCCallback() {     //�ص����
                            @Override
                            public void done(BCResult bcResult) {

                                //�˴��ر�loading����
                                loadingDialog.dismiss();

                                final BCQRCodeResult bcqrCodeResult = (BCQRCodeResult) bcResult;

                                //resultCodeΪ0��ʾ����ɹ�
                                if (bcqrCodeResult.getResultCode() == 0) {
                                    wxQRBitmap = bcqrCodeResult.getQrCodeBitmap();
                                    Log.w(Tag, "weixin qrcode url: " + bcqrCodeResult.getQrCodeRawContent());

                                } else {

                                    GenQRCodeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GenQRCodeActivity.this, "err code:" + bcqrCodeResult.getResultCode() +
                                                    "; err msg: " + bcqrCodeResult.getResultMsg() +
                                                    "; err detail: " + bcqrCodeResult.getErrDetail(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }

                                Message msg = mHandler.obtainMessage();
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        });
            }
        });


        btnReqALIQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();

                Map<String, String> mapOptional = new HashMap<String, String>();

                mapOptional.put("testalikey1", "����valueֵ1");
                BCPay.getInstance(GenQRCodeActivity.this).reqAliQRCodeAsync("֧������Ƕ��ά��֧������",   //��Ʒ����
                        1,                                                  //�ܽ��, �Է�Ϊ��λ, ������������
                        UUID.randomUUID().toString().replace("-", ""),      //��ˮ��
                        mapOptional,                                        //��չ����
                        "https://beecloud.cn/",  //֧���ɹ�֮��ķ���url
                        "1",                          /* ע�� ��ά�����ͺ���
                                                        * null��֧��������Ĭ������, ������
                                                        * "0": ������-��Լǰ��ģʽ, ��Ӧ iframe ��Ȳ���С�� 600px, �߶Ȳ���С�� 300px
                                                        * "1": ������-ǰ��ģʽ, ��Ӧ iframe ��Ȳ���С�� 300px, �߶Ȳ���С�� 600px
                                                        * "3": ������-����ǰ��ģʽ, ��Ӧ iframe ��Ȳ���С�� 75px, �߶Ȳ���С�� 75px
                                                        */
                        new BCCallback() {     //�ص����
                            @Override
                            public void done(BCResult bcResult) {

                                //�˴��ر�loading����
                                loadingDialog.dismiss();

                                final BCQRCodeResult bcqrCodeResult = (BCQRCodeResult) bcResult;

                                //resultCodeΪ0��ʾ����ɹ�
                                if (bcqrCodeResult.getResultCode() == 0) {
                                    aliQRURL = bcqrCodeResult.getQrCodeRawContent();
                                    aliQRHtml = bcqrCodeResult.getAliQRCodeHtml();
                                    Log.w(Tag, "ali qrcode url: " + aliQRURL);
                                    Log.w(Tag, "ali qrcode html: " + aliQRHtml);

                                } else {

                                    GenQRCodeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GenQRCodeActivity.this, "err code:" + bcqrCodeResult.getResultCode() +
                                                    "; err msg: " + bcqrCodeResult.getResultMsg() +
                                                    "; err detail: " + bcqrCodeResult.getErrDetail(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }

                                Message msg = mHandler.obtainMessage();
                                msg.what = 2;
                                mHandler.sendMessage(msg);
                            }
                        });
            }
        });

        btnReqALIOfflineQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();

                Map<String, String> mapOptional = new HashMap<String, String>();

                mapOptional.put("testkey1", "����valueֵ1");
                BCPay.getInstance(GenQRCodeActivity.this).reqAliOfflineQRCodeAsync("֧�������¶�ά��֧������", //��Ʒ����
                        1,                          //�ܽ��, �Է�Ϊ��λ, ������������
                        UUID.randomUUID().toString().replace("-", ""),          //��ˮ��
                        mapOptional,            //��չ����
                        true,                   //�Ƿ����ɶ�ά���bitmap,
                                                //���Ϊfalse�������и���getQrCodeRawContent���صĽ��
                                                //ʹ��BCPay.generateBitmap��������֧����ά��
                                                //��Ҳ����ʹ���Լ���Ϥ�Ķ�ά�����ɹ���
                        null,                   //��ά��ĳߴ�, ��pxΪ��λ, ���Ϊnull��Ĭ��Ϊ360
                        new BCCallback() {     //�ص����
                            @Override
                            public void done(BCResult bcResult) {

                                //�˴��ر�loading����
                                loadingDialog.dismiss();

                                final BCQRCodeResult bcqrCodeResult = (BCQRCodeResult) bcResult;

                                //resultCodeΪ0��ʾ����ɹ�
                                if (bcqrCodeResult.getResultCode() == 0) {
                                    aliOfflineQRBitmap = bcqrCodeResult.getQrCodeBitmap();
                                    //Log.w(Tag, "ali offline qrcode url: " + bcqrCodeResult.getQrCodeRawContent());

                                } else {

                                    GenQRCodeActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GenQRCodeActivity.this, "err code:" + bcqrCodeResult.getResultCode() +
                                                    "; err msg: " + bcqrCodeResult.getResultMsg() +
                                                    "; err detail: " + bcqrCodeResult.getErrDetail(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }

                                Message msg = mHandler.obtainMessage();
                                msg.what = 3;
                                mHandler.sendMessage(msg);
                            }
                        });
            }
        });

    }
}
