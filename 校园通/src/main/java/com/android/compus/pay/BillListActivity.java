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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cn.beecloud.BCQuery;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCBillOrder;
import cn.beecloud.entity.BCQueryBillOrderResult;
import cn.beecloud.entity.BCReqParams;

import com.android.compus.R;

/**
 * ����չʾ������ѯ
 */
public class BillListActivity extends Activity {
    public static final String TAG = "BillListActivity";

    Button btnWeChatOrder;
    Button btnAliPayOrder;
    Button btnUNPayOrder;
    Button btnPayPalPayOrder;
    Button btnAllPayOrder;
    ListView listViewOrder;

    private Handler mHandler;
    private List<BCBillOrder> bills;

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        loadingDialog = new ProgressDialog(BillListActivity.this);
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

                    BillListAdapter adapter = new BillListAdapter(
                            BillListActivity.this, bills);
                    listViewOrder.setAdapter(adapter);
                }
                return true;
            }
        });


        listViewOrder = (ListView) findViewById(R.id.listViewOrder);

        //�ص����
        final BCCallback bcCallback = new BCCallback() {
            @Override
            public void done(BCResult bcResult) {

                //�˴��ر�loading����
                loadingDialog.dismiss();

                final BCQueryBillOrderResult bcQueryResult = (BCQueryBillOrderResult) bcResult;

                //resultCodeΪ0��ʾ����ɹ�
                //count�������صĶ�������
                if (bcQueryResult.getResultCode() == 0) {

                    //�����б�
                    bills = bcQueryResult.getBills();

                    Log.i(BillListActivity.TAG, "bill count: " + bcQueryResult.getCount());

                } else {
                    //�����б�
                    bills = null;

                    BillListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BillListActivity.this, "err code:" + bcQueryResult.getResultCode() +
                                    "; err msg: " + bcQueryResult.getResultMsg() +
                                    "; err detail: " + bcQueryResult.getErrDetail(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };

        btnWeChatOrder = (Button) findViewById(R.id.btnWeChatOrder);
        btnWeChatOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // �������֧��̫�������������￪����������ʾ����loading
                //��progressdialogΪ��
                loadingDialog.show();

                BCQuery.getInstance().queryBillsAsync(
                        BCReqParams.BCChannelTypes.WX_APP,  //�˴���ʾ΢��app��֧���Ĳ�ѯ
                        bcCallback);
            }
        });

        btnAliPayOrder = (Button) findViewById(R.id.btnAliPayOrder);
        btnAliPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();

                BCQuery.getInstance().queryBillsAsync(
                        BCReqParams.BCChannelTypes.ALI_APP, //����
                        "20150820102712150", //������
                        bcCallback);
            }
        });

        btnUNPayOrder = (Button) findViewById(R.id.btnUNPayOrder);
        btnUNPayOrder.setOnClickListener(new View.OnClickListener() {
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

                BCQuery.getInstance().queryBillsAsync(
                        BCReqParams.BCChannelTypes.UN,          //����, �˴���ʾ���е�����֧��
                        null,                                   //������
                        startTime.getTime(),                    //��������ʱ��
                        endTime.getTime(),                      //�������ʱ��
                        2,                                      //��������������ǰ2������
                        15,                                      //ֻ��������������15������
                        bcCallback);
            }
        });

        btnPayPalPayOrder = (Button) findViewById(R.id.btnPayPalPayOrder);
        btnPayPalPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // �������֧��̫�������������￪����������ʾ����loading
                //��progressdialogΪ��
                loadingDialog.show();

                BCQuery.getInstance().queryBillsAsync(
                        BCReqParams.BCChannelTypes.PAYPAL,  //�˴���ʾPAYPAL֧���Ĳ�ѯ
                        bcCallback);
            }
        });

        btnAllPayOrder = (Button) findViewById(R.id.btnAllPayOrder);
        btnAllPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.show();

                BCQuery.getInstance().queryBillsAsync(
                        BCReqParams.BCChannelTypes.ALL, //ȫ������ѯ
                        bcCallback);
            }
        });
    }

}
