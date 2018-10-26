package com.smsreader.smsPresenter.view;

import com.smsreader.database.RealmManager;
import com.smsreader.model.SMSModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SMSPresenter {

    SMSView view;

    public SMSPresenter(SMSView view)
    {
        this.view = view;

    }


    public void getAllSMS()
    {
        RealmManager manager = RealmManager.getInstance();
       List<SMSModel> smsList = new ArrayList<>(manager.getAllSMS());
        Collections.reverse(smsList);
        view.publishSMSList(smsList);
    }
}
