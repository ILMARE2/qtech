package com.android.compus.pay;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
import cn.beecloud.BCQuery;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCQueryRefundStatusResult;
import cn.beecloud.entity.BCReqParams;

import com.android.compus.R;

/**
 * ����չʾ�˿�״̬
 */
public class RefundStatusActivity extends Activity {
    public static final String TAG = "BillListActivity";

    TextView txtRefundStatus;

    private ProgressDialog loadingDialog;
    private Handler mHandler;
    private String refundStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_status);

        txtRefundStatus = (TextView) findViewById(R.id.txtRefundStatus);

        loadingDialog = new ProgressDialog(RefundStatusActivity.this);
        loadingDialog.setMessage("�������������, ���Ժ�...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(true);

        // Defines a Handler object that's attached to the UI thread.
        // ͨ��Handler.Callback()�������ڴ�й©����
        mHandler = new Handler(new Handler.Callback() {
            /**
             * Callback interface you can use when instantiating a Handler to
             * avoid having to implement your own subclass of Handler.
             *
             * handleMessage() defines the operations to perform when the
             * Handler receives a new Message to process.
             */
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {

                    txtRefundStatus.setText(
                            BCQueryRefundStatusResult.RefundStatus.getTranslatedRefundStatus(refundStatus));
                }
                return true;
            }
        });

        //�ص����
        final BCCallback bcCallback = new BCCallback() {
            @Override
            public void done(BCResult bcResult) {

                //�˴��ر�loading����
                loadingDialog.dismiss();

                final BCQueryRefundStatusResult bcQueryResult = (BCQueryRefundStatusResult) bcResult;

                //resultCodeΪ0��ʾ����ɹ�
                if (bcQueryResult.getResultCode() == 0) {

                    //���ص��˿���Ϣ
                    refundStatus = bcQueryResult.getRefundStatus();

                    if (refundStatus == null){

                        RefundStatusActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RefundStatusActivity.this, "û�в�ѯ�������Ϣ", Toast.LENGTH_LONG).show();
                            }
                        });

                    }else{
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }

                } else {

                    RefundStatusActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RefundStatusActivity.this, "err code:" + bcQueryResult.getResultCode() +
                                    "; err msg: " + bcQueryResult.getResultMsg() +
                                    "; err detail: " + bcQueryResult.getErrDetail(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        };

        loadingDialog.show();
        BCQuery.getInstance().queryRefundStatusAsync(
                BCReqParams.BCChannelTypes.WX,     //Ŀǰ��֧��΢��
                "20150812436857",                   //������΢�ŵ��˿��
                bcCallback);                            //�ص����
    }

}
