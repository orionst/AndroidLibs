package ru.geekbrains.android3_1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageConverter {

    String storageDirName;

    public ImageConverter(String storageDirName) {
        this.storageDirName = storageDirName;
    }

    public byte[] convertImageToArray(String filepath) {
        Bitmap bmp = BitmapFactory.decodeFile(filepath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bmp.recycle();
        return stream.toByteArray();
    }

    public void createImageFromData(byte[] arr, String fileName) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            File convertedImage = new File(storageDirName + fileName);
            FileOutputStream outStream = new FileOutputStream(convertedImage);
            outStream.write(arr);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
