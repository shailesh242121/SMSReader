package com.smsreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class SMSModel extends RealmObject implements Parcelable {
    private String sender;
    private String message;
    private String time;

    public static final Creator<SMSModel> CREATOR = new Creator<SMSModel>() {
        @Override
        public SMSModel createFromParcel(Parcel in) {
            return new SMSModel(in);
        }

        @Override
        public SMSModel[] newArray(int size) {
            return new SMSModel[size];
        }
    };

    public SMSModel() {

    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sender);
        dest.writeString(this.time);
        dest.writeString(this.message);
    }

    // Parcelling part
    public SMSModel(Parcel in){
        this.sender = in.readString();
        this.time = in.readString();
        this.message =  in.readString();
    }


}
