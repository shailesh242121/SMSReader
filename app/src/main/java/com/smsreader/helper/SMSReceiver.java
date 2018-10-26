package com.smsreader.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.smsreader.R;
import com.smsreader.database.RealmManager;
import com.smsreader.interfaces.ISMSListener;
import com.smsreader.model.SMSModel;

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";


    private ISMSListener listener;
    public SMSReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            StringBuilder smsBody = new StringBuilder();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody.append(smsMessage.getMessageBody());
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody.append(messages[i].getMessageBody());
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            if(!(TextUtils.isEmpty(smsSender) || TextUtils.isEmpty(smsBody)))
            {
                SMSModel sms  = new SMSModel();
                sms.setSender(smsSender);
                sms.setMessage(smsBody.toString());
                sms.setTime(Utility.getFormatedDate());

//                if(listener!=null)
//                {
//                    listener.onSMSReceive(sms);
//                }


                Intent i = new Intent(context.getString(R.string.sms_broadcast_filter));
                Bundle m = new Bundle();
                m.putParcelable("sms_data",sms);
                i.putExtras(m);
                LocalBroadcastManager.getInstance(context).sendBroadcast(i);

                RealmManager manager  = RealmManager.getInstance();
                manager.saveSMS(sms);

            }

        }
    }



    public void setSMSListener(ISMSListener listener) {
        this.listener = listener;
    }




}