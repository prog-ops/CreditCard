package com.example.empatkali.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Facedata {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private String image_name;

    private String image_data;

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

    public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }
}
