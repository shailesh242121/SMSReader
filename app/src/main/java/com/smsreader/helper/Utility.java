package com.smsreader.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static String getFormatedDate()
    {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY HH:ss");
        return sdf.format(today);
    }
}
