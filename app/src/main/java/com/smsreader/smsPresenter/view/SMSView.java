package com.smsreader.smsPresenter.view;

import com.smsreader.model.SMSModel;

import java.util.List;

public interface SMSView {
    void publishSMSList(List<SMSModel> smsList);
}
