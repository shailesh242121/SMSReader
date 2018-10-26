package com.smsreader.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smsreader.R;
import com.smsreader.smsPresenter.view.SMSPresenter;
import com.smsreader.smsPresenter.view.SMSView;
import com.smsreader.adapter.SMSAdapter;
import com.smsreader.model.SMSModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements SMSView {

    @BindView(R.id.rcl_smsReader)
    RecyclerView rclSMSReader;

    private SMSAdapter smsAdapter;
    private SMSPresenter mPresenter;
    private List<SMSModel> smsList= new ArrayList<>();


    private final int SMS_PERMISSION_CODE  = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupValues();
    }

    private void setupValues() {
         mPresenter = new SMSPresenter(this);
        if(isSmsPermissionGranted()) {
            retrieveListFromDatabase();

        }else
        {
            requestReadAndSendSmsPermission();
        }
    }

    private void retrieveListFromDatabase()
    {
        mPresenter.getAllSMS();
    }

    private void setupAdapter() {
        rclSMSReader.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        smsAdapter = new SMSAdapter(smsList);
        rclSMSReader.setAdapter(smsAdapter);
    }



    /**
     * Check if we have SMS permission
     */
    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        if(requestCode == SMS_PERMISSION_CODE) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Home.this, "Now app can see SMS", Toast.LENGTH_SHORT).show();

                } else {
                    showErrorDialog();
                }

            }

    }

    /**
     * Showing error Dialog when permission not granted
     */
    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Please allow sms permission to read sms")
                .setPositiveButton("Allow", new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestReadAndSendSmsPermission();

                    }
                } )
                .setNegativeButton("Close Application", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }


//    @Override
//    public void onSMSReceive(SMSModel sms) {
////         smsList.add(sms);
////         smsAdapter.notifyDataSetChanged();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveListFromDatabase();
        LocalBroadcastManager.getInstance(this).registerReceiver(mSmsReceiver,new IntentFilter(getString(R.string.sms_broadcast_filter)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSmsReceiver);
    }

   private BroadcastReceiver mSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle mBundle = intent.getExtras();
            if(mBundle!=null)
            {
                SMSModel model = mBundle.getParcelable("sms_data");
                smsList.add(0,model);
                if(smsAdapter != null)
                smsAdapter.notifyDataSetChanged();
                else retrieveListFromDatabase();
            }

        }
    };


    @Override
    public void publishSMSList(List<SMSModel> databaseList) {
        smsList.clear();
        smsList.addAll(databaseList);
        setupAdapter();
    }
}
