package com.smsreader.ui.activity;

import android.app.Application;
import android.content.IntentFilter;
import android.provider.Telephony;

import com.smsreader.BuildConfig;
import com.smsreader.helper.SMSReceiver;
import com.smsreader.interfaces.ISMSListener;

import io.realm.Realm;

public class SMSReaderApplication extends Application {

        private SMSReceiver smsBroadcastReceiver;

        @Override
        public void onCreate() {
            super.onCreate();
            smsBroadcastReceiver = new SMSReceiver();
           // registerReceiver(smsBroadcastReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
            Realm.init(this);
        }

        @Override
        public void onTerminate() {
           // unregisterReceiver(smsBroadcastReceiver);
            super.onTerminate();
        }

    public void setSMSListener(ISMSListener listener) {
        smsBroadcastReceiver.setSMSListener(listener);
    }
}
