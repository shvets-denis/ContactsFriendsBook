package com.valerityoss.contactsfriendsbook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.valerityoss.contactsfriendsbook.database.AppDatabase;

/**
 * Created by denis on 26.04.2016.
 */
@Table(database = AppDatabase.class)
public class Friend extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public String phone;
    @Column
    public String email;
    @Column
    public int gender;
    @Column
    public String urlPhoto;

    protected Friend(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
        gender = in.readInt();
        urlPhoto = in.readString();
    }

    public Friend() {

    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(gender);
        dest.writeString(urlPhoto);
    }
}
