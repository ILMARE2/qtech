package com.android.compus.pay;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.beecloud.BCPay;
import cn.beecloud.BeeCloud;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;

import com.android.compus.R;
import com.android.compus.bean.Order;
import com.unionpay.UPPayAssistEx;


public class ShoppingCartActivity extends Activity {

    Button btnQueryOrders;

    private ProgressDialog loadingDialog;
    private ListView payMethod;
    
    private TextView tv_shop_name;
    private TextView tv_price;
    private TextView tv_good_name;
    private TextView tv_good_id;
    
    //֧������������
    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(final BCResult bcResult) {
            final BCPayResult bcPayResult = (BCPayResult)bcResult;
            //�˴��ر�loading����
            loadingDialog.dismiss();

            //�����ͨ��Toast֪ͨ�û��������ʹ�����·�ʽ��
            // ֱ��makeText�п��ܻ����java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
            ShoppingCartActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String result = bcPayResult.getResult();

                    //ע�⣡
                    //����PayPal��֧�������ص�RESULT_SUCCESS���������ֻ���֧���ɹ���
                    //����ÿһ��֧����sdk���Զ�����������ͬ����
                    // ����Ҫ�鿴server�˵�֧�������ȷ���������׼ȷ���󣬱���������ǿ����Ҫ�󣬵�PayPal�ٷ��Ƽ��Է�ֹ��թ�ֶ�
                    //�������������ͬ��ʧ�ܣ���¼�ᱻ�Զ����棬��ʱ����Ե���batchSyncPayPalPayment�����ֶ�ͬ��
                    //��Ȼ��������Ƚ��٣����ǽ���ο�PayPalUnSyncedListActivity����ͬ����������������޷����ĵ�����
                    if (result.equals(BCPayResult.RESULT_SUCCESS))
                        Toast.makeText(ShoppingCartActivity.this, "�û�֧���ɹ�", Toast.LENGTH_LONG).show();
                    else if (result.equals(BCPayResult.RESULT_CANCEL))
                        Toast.makeText(ShoppingCartActivity.this, "�û�ȡ��֧��", Toast.LENGTH_LONG).show();
                    else if(result.equals(BCPayResult.RESULT_FAIL)) {
                        Toast.makeText(ShoppingCartActivity.this, "֧��ʧ��, ԭ��: " + bcPayResult.getErrMsg()
                                + ", " + bcPayResult.getDetailInfo(), Toast.LENGTH_LONG).show();

                        if (bcPayResult.getErrMsg().equals(BCPayResult.FAIL_PLUGIN_NOT_INSTALLED) ||
                                bcPayResult.getErrMsg().equals(BCPayResult.FAIL_PLUGIN_NEED_UPGRADE)) {
                            //������Ҫ���°�װ�ؼ�
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    } else if (result.equals(BCPayResult.RESULT_UNKNOWN)) {
                        //���ܳ�����֧����8000����״̬
                        Toast.makeText(ShoppingCartActivity.this, "����״̬δ֪", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(ShoppingCartActivity.this, "invalid return", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    };

    // Defines a Handler object that's attached to the UI thread.
    // ͨ��Handler.Callback()�������ڴ�й©����
    private Handler mHandler = new Handler(new Handler.Callback() {
        /**
         * Callback interface you can use when instantiating a Handler to avoid
         * having to implement your own subclass of Handler.
         *
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                //����û��ֻ�û�а�װ����֧���ؼ�,�����ʾ�û���װ
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setTitle("��ʾ");
                builder.setMessage("���֧����Ҫ��װ����֧���ؼ����Ƿ�װ��");

                builder.setNegativeButton("ȷ��",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UPPayAssistEx.installUPPayPlugin(ShoppingCartActivity.this);
                                dialog.dismiss();
                            }
                        });

                builder.setPositiveButton("ȡ��",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Bundle bundle=getIntent().getBundleExtra("order");
        Order order=(Order) bundle.getSerializable("order");
        
        tv_good_id=(TextView) findViewById(R.id.good_id);
        tv_good_name=(TextView) findViewById(R.id.tv_good_name);
        	tv_price=(TextView) findViewById(R.id.tv_price);
        	tv_shop_name=(TextView) findViewById(R.id.tv_shop_name);
        	
        	tv_good_id.setText(UUID.randomUUID().toString().replaceAll("-", ""));
        	tv_shop_name.setText(order.getShopName());
        	tv_good_name.setText(order.getGoodName());
        	tv_price.setText(order.getPrice()+"");
        		
        // �Ƽ�����Activity���onCreate�����г�ʼ��BeeCloud.
        BeeCloud.setAppIdAndSecret("c5d1cba1-5e3f-4ba0-941d-9b0a371fe719", "39a7a518-9ac8-4a9e-87bc-7885f33cf18c");

        // ����õ�΢��֧�������õ�΢��֧����Activity��onCreate������������º���.
        // �ڶ���������Ҫ�������Լ���΢��AppID.
        BCPay.initWechatPay(ShoppingCartActivity.this, "wx19433a59b15fe84d");

        // ���ʹ��PayPal��Ҫ��֧��֮ǰ����client id��Ӧ��secret
        // BCPay.PAYPAL_PAY_TYPE.SANDBOX���ڲ��ԣ�BCPay.PAYPAL_PAY_TYPE.LIVE������������
        BCPay.initPayPal("AVT1Ch18aTIlUJIeeCxvC7ZKQYHczGwiWm8jOwhrREc4a5FnbdwlqEB4evlHPXXUA67RAAZqZM0H8TCR",
                "EL-fkjkEUyxrwZAmrfn46awFXlX-h2nRkyCVhhpeVdlSRuhPJKXx3ZvUTTJqPQuAeomXA8PZ2MkX24vF",
                BCPay.PAYPAL_PAY_TYPE.SANDBOX, Boolean.FALSE);

        payMethod = (ListView) this.findViewById(R.id.payMethod);
        Integer[] payIcons = new Integer[]{R.drawable.wechat, R.drawable.alipay,
                R.drawable.unionpay, R.drawable.paypal, R.drawable.scan};
        String[] payNames = new String[]{"΢��֧��", "֧����֧��",
                "��������", "PayPal֧��", "��ά��֧��"};
        String[] payDescs = new String[]{"ʹ��΢��֧�����������CNY�Ʒ�", "ʹ��֧����֧�����������CNY�Ʒ�",
                "ʹ����������֧�����������CNY�Ʒ�", "ʹ��PayPal֧��������ԪUSD�Ʒ�", "ͨ��ɨ���ά��֧��"};
        PayMethodListItem adapter = new PayMethodListItem(this, payIcons, payNames, payDescs);
        payMethod.setAdapter(adapter);

        // �������֧��̫��, ���������￪������, ��progressdialogΪ��
        loadingDialog = new ProgressDialog(ShoppingCartActivity.this);
        loadingDialog.setMessage("����������֧�������Ժ�...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(true);

        
        
        payMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0: //΢��
                        loadingDialog.show();
                        //����΢��֧��, �ֻ��ڴ�̫С����OutOfResourcesException��ɵĿ���, �����޷����֧��
                        //�����΢��������ڵ�����
                        Map<String, String> mapOptional = new HashMap<String, String>();

                        mapOptional.put("testkey1", "����valueֵ1");   //map��key��ʱ��֧������

                        if (BCPay.isWXAppInstalledAndSupported() &&
                                BCPay.isWXPaySupported()) {

                            BCPay.getInstance(ShoppingCartActivity.this).reqWXPaymentAsync(
                                    "΢��֧������",               //��������
                                    1,                           //�������(��)
                                    UUID.randomUUID().toString().replace("-", ""),  //������ˮ��
                                    mapOptional,            //��չ����(����null)
                                    bcCallback);            //֧����ɺ�ص����
                        }
                        break;

                    case 1: //֧����֧��
                        loadingDialog.show();

                        mapOptional = new HashMap<String, String>();
                        mapOptional.put("paymentid", "");
                        mapOptional.put("consumptioncode", "consumptionCode");
                        mapOptional.put("money", "2");

                        BCPay.getInstance(ShoppingCartActivity.this).reqAliPaymentAsync("֧����֧������", 1,
                                UUID.randomUUID().toString().replace("-", ""), mapOptional, bcCallback);
                        break;

                    case 2: //����֧��
                        loadingDialog.show();

                        BCPay.getInstance(ShoppingCartActivity.this).reqUnionPaymentAsync("����֧������", 1,
                                UUID.randomUUID().toString().replace("-", ""), null, bcCallback);
                        break;
                    case 3: //ͨ��PayPal֧��
                        loadingDialog.show();

                        HashMap<String, String> hashMapOptional = new HashMap<String, String>();
                        hashMapOptional.put("PayPal key1", "PayPal value1");
                        hashMapOptional.put("PayPal key2", "PayPal value2");

                        BCPay.getInstance(ShoppingCartActivity.this).reqPayPalPaymentAsync("PayPal payment test", 1,
                                hashMapOptional, bcCallback);
                        break;
                    default:
                        Intent intent = new Intent(ShoppingCartActivity.this, GenQRCodeActivity.class);
                        startActivity(intent);
                }
            }
        });

        btnQueryOrders = (Button) findViewById(R.id.btnQueryOrders);
        btnQueryOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, OrdersEntryActivity.class);
                startActivity(intent);
            }
        });
    }

}
