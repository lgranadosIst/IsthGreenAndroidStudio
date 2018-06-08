package com.isthmusit.isthgreen.isthgreenapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String Username;

    public String Password;

    public User(){}

    protected User(Parcel in) {
        Username = in.readString();
        Password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Username);
        dest.writeString(Password);
    }
}
