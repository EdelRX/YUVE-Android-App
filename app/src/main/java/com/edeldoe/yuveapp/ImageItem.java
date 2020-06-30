package com.edeldoe.yuveapp;

import android.graphics.Bitmap;

/**
 * Created by EdelRX on 03/03/2016.
 */
public class ImageItem {
    public Bitmap image;

    public ImageItem(Bitmap image) {
        super();
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}