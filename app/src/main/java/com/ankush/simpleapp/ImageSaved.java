package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;

public class ImageSaved extends AppCompatActivity {
    ImageView imageView;
    Button cancel , added;
    String result;
    Uri fileuri;
    OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_saved);

        readIntent();

        imageView = findViewById(R.id.previwe);
        cancel = findViewById(R.id.cancel);
        added = findViewById(R.id.gallery);
        imageView.setImageURI(fileuri);
        cancel = findViewById(R.id.cancel);


        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveImage();

                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                saveImageToExternalStorage( UUID.randomUUID().toString() , bitmap);

                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
        // Read Intent Passed by Activity_ (Gallery || Selfie )
    private void readIntent() {
        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            result=intent.getStringExtra("DATA");
            fileuri = Uri.parse(result);
        }
    }

        // This function is broken due to something wrong
    private void saveImage() {

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();



        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsoluteFile()+"/SaveImage/");

        if (!dir.exists()){

            dir.mkdir();

        }



        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,90,outputStream);
        Toast.makeText(ImageSaved.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function used to Stored the Image into External Media
    private boolean saveImageToExternalStorage(String imgName, Bitmap bmp){

        Uri ImageCollection = null;
        ContentResolver resolver = getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            ImageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        }else {

            ImageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
        Uri imageUri = resolver.insert(ImageCollection, contentValues);

        try {

            OutputStream outputStream = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            bmp.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(ImageSaved.this,"saved Image Successfully" ,Toast.LENGTH_SHORT).show();

            return true;

        }
        catch (Exception e){

            Toast.makeText(this,"Image not saved: \n" + e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

        return false;


    }

}