package com.mickleentityltdnigeria.resturantapp.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.exifinterface.media.ExifInterface;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.AppGlobals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ImageHelper {

        public static ImageHelper mInstant;

        public static ImageHelper getInstant(){
            if(mInstant==null){
                mInstant = new ImageHelper();
            }
            return mInstant;
        }

        public String byteArrayToString(byte[] byteArray){
            return  Base64.encodeToString(byteArray, Base64.URL_SAFE);
            //  store & retrieve this string which is URL safe(can be used to store in FB DB) to firebase
            // Use either Realtime Database or Firebase.
        }

        public byte[] base64StringToByteArray(String base64String){
            byte[] value = new byte[0];
            try{
                value = Base64.decode(base64String.getBytes("UTF-8"),Base64.URL_SAFE);
            }catch (UnsupportedEncodingException e){
                Toast.makeText(AppGlobals.getAppContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
             return value;
        }

    // Decodes image and scales it to reduce memory consumption
    public byte[] decodeFile(Uri uri){
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream( AppGlobals.getAppContext().getContentResolver().openInputStream(uri), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=600;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap scaledBitmap = BitmapFactory.decodeStream(AppGlobals.getAppContext().getContentResolver().openInputStream(uri), null, o2);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
            byte[] b = out.toByteArray();
            int size = b.length;
            //Toast.makeText(AppGlobals.getAppContext(),String.valueOf(b.length),Toast.LENGTH_LONG).show();
            if(size > 1000000){
                throw new Exception("Image size:" + size + ", image size can not be above 1 mega byte,");
            }
            return b;
        } catch (Exception e) {

        }
        return null;
    }

        public byte[] getCompressedBitmap(String imagePath, int maxSize) throws Exception {
            float maxHeight = 1920.0f;
            float maxWidth = 1080.0f;
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float imgRatio = (float) actualWidth / (float) actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(imagePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }
            //
           /* int size = bmp.getAllocationByteCount();
            if( size > maxSize){
                throw new Exception((" Actual size:" + size + ", Image size is greater than " + maxSize));
            }*/
            //
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);

            //byte[] byteArray = out.toByteArray();

            return out.toByteArray();
            //Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            //return updatedBitmap;
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
            return inSampleSize;
        }

    //image processing
    public Drawable imageFromString(String imgString){
        InputStream ims = new ByteArrayInputStream(ImageHelper.getInstant().base64StringToByteArray(imgString));
        Drawable d = Drawable.createFromStream(ims, null);
        return d;
    }
    public Drawable imageFromByteArray(byte[] imgArray){
        InputStream ims = new ByteArrayInputStream(imgArray);
        Drawable d = Drawable.createFromStream(ims, null);
        return d;
    }

}
