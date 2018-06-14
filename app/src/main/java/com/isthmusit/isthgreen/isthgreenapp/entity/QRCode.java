package com.isthmusit.isthgreen.isthgreenapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class QRCode implements Parcelable {

    private String _qrCode;

    public QRCode(){}
    protected QRCode(Parcel in){
        this._qrCode = in.readString();
    }
    public String getQrCode(){
        return this._qrCode;
    }
    public void setQrCode(String qrCode){
        this._qrCode = qrCode;
    }


    public static final Parcelable.Creator<QRCode> CREATOR = new Parcelable.Creator<QRCode>() {
        public QRCode createFromParcel(Parcel in) {
            return new QRCode(in);
        }

        public QRCode[] newArray(int size) {
            return new QRCode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._qrCode);
    }

    @Override
    public String toString(){
        return "QR Code: " + this._qrCode;
    }

}
