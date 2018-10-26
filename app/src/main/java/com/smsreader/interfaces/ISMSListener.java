package com.smsreader.interfaces;

import com.smsreader.model.SMSModel;

public interface ISMSListener {
    void onSMSReceive(SMSModel sms);
}
