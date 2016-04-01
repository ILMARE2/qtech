package com.android.compus.pay;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.compus.R;

public class OrdersEntryActivity extends Activity {
    Button btnQueryBills;
    Button btnQueryRefunds;
    Button btnRefundStatus;
    Button btnPayPalUnSynced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_entry);

        btnQueryBills = (Button) findViewById(R.id.btnQueryBills);
        btnQueryBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersEntryActivity.this, BillListActivity.class);
                startActivity(intent);
            }
        });

        btnQueryRefunds = (Button) findViewById(R.id.btnQueryRefunds);
        btnQueryRefunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersEntryActivity.this, RefundOrdersActivity.class);
                startActivity(intent);
            }
        });

        btnRefundStatus = (Button) findViewById(R.id.btnRefundStatus);
        btnRefundStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersEntryActivity.this, RefundStatusActivity.class);
                startActivity(intent);
            }
        });

        btnPayPalUnSynced = (Button) findViewById(R.id.btnPayPalUnSynced);
        btnPayPalUnSynced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersEntryActivity.this, PayPalUnSyncedListActivity.class);
                startActivity(intent);
            }
        });
    }

}
