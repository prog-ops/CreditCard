package com.example.empatkali.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Facedata implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private String image_name;

//    private String image_data;

    public Facedata(){}

    protected Facedata(Parcel in) {
        id = in.readLong();
        image = in.createByteArray();
        image_name = in.readString();
    }

    public static final Creator<Facedata> CREATOR = new Creator<Facedata>() {
        @Override
        public Facedata createFromParcel(Parcel in) {
            return new Facedata(in);
        }

        @Override
        public Facedata[] newArray(int size) {
            return new Facedata[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeByteArray(image);
        parcel.writeString(image_name);
    }

    /*public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }*/
}
