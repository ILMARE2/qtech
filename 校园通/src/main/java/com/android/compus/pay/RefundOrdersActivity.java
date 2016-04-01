package com.android.compus.pay;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cn.beecloud.BCQuery;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCQueryRefundOrderResult;
import cn.beecloud.entity.BCRefundOrder;
import cn.beecloud.entity.BCReqParams;

import com.android.compus.R;

/**
 * ����չʾ�˿����ѯ
 */
public class RefundOrdersActivity extends Activity {

    public static final String TAG = "RefundOrdersActivity";

    Button btnWeChatRefundOrder;
    Button btnAliPayRefundOrder;
    Button btnUNPayRefundOrder;
    Button btnAllPayRefundOrder;
    ListView listViewRefundOrder;

    private Handler mHandler;
    private List<BCRefundOrder> refundOrders;

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_orders);

        loadingDialog = new ProgressDialog(RefundOrdersActivity.this);
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

                    RefundOrdersAdapter adapter = new RefundOrdersAdapter(
                            RefundOrdersActivity.this, refundOrders);
                    listViewRefundOrder.setAdapter(adapter);
                }
                return true;
            }
        });


        listViewRefundOrder = (ListView) findViewById(R.id.listViewRefundOrder);
        listViewRefundOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //�ص����
        final BCCallback bcCallback = new BCCallback() {
            @Override
            public void done(BCResult bcResult) {

                //�˴��ر�loading����
                loadingDialog.dismiss();

                final BCQueryRefundOrderResult bcQueryRefundOrderResult = (BCQueryRefundOrderResult) bcResult;

                //resultCodeΪ0��ʾ����ɹ�
                //count�������صĶ�������
                if (bcQueryRefundOrderResult.getResultCode() == 0) {

                    //�����б�
                    refundOrders = bcQueryRefundOrderResult.getRefunds();

                    Log.i(BillListActivity.TAG, "bill count: " + bcQueryRefundOrderResult.getCount());

                } else {
                    //�����б�
                    refundOrders = null;

                    RefundOrdersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RefundOrdersActivity.this, "err code:" + bcQueryRefundOrderResult.getResultCode() +
                                    "; err msg: " + bcQueryRefundOrderResult.getResultMsg() +
                                    "; err detail: " + bcQueryRefundOrderResult.getErrDetail(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };

        btnWeChatRefundOrder = (Button) findViewById(R.id.btnWeChatRefundOrder);
        btnWeChatRefundOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // �������֧��̫�������������￪����������ʾ����loading
                //��progressdialogΪ��
                loadingDialog.show();

                BCQuery.getInstance().queryRefundsAsync(
                        BCReqParams.BCChannelTypes.WX_APP,      //ֱ����������ʽ��ѯ
                        bcCallback);
            }
        });

        btnAliPayRefundOrder = (Button) findViewById(R.id.btnAliPayRefundOrder);
        btnAliPayRefundOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();

                BCQuery.getInstance().queryRefundsAsync(
                        BCReqParams.BCChannelTypes.ALI,     //����
                        "883dafee43b54c68a1dc7cf24f705463",     //������
                        "20150812436857",                     //�̻��˿���ˮ��
                        bcCallback);
            }
        });

        btnUNPayRefundOrder = (Button) findViewById(R.id.btnUNPayRefundOrder);
        btnUNPayRefundOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date startTime, endTime;
                try {
                    startTime = sdf.parse("2015-08-01 00:00");
                    endTime = sdf.parse("2015-08-31 23:59");
                } catch (ParseException e) {
                    startTime = new Date();
                    endTime = new Date();
                    e.printStackTrace();
                }

                BCQuery.getInstance().queryRefundsAsync(
                        BCReqParams.BCChannelTypes.UN,          //����
                        null,                                   //������
                        null,                                   //�̻��˿���ˮ��
                        startTime.getTime(),                    //�˿������ʱ��
                        endTime.getTime(),                      //�˿�����ʱ��
                        1,                                      //��������������ǰ1������
                        15,                                      //ֻ��������������15������
                        bcCallback);
            }
        });

        btnAllPayRefundOrder = (Button) findViewById(R.id.btnAllPayRefundOrder);
        btnAllPayRefundOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();

                BCQuery.getInstance().queryRefundsAsync(
                        BCReqParams.BCChannelTypes.ALL,     //����
                        bcCallback);
            }
        });
    }

}
