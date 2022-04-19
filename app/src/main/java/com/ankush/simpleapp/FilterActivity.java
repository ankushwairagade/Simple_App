package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.uvstudio.him.photofilterlibrary.PhotoFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;


public class FilterActivity extends AppCompatActivity {

    Button Nofilter,filter1,filter2,filter3,filter4,filter5 ,filter6,filter7,filter8 ,filter9,filter10,filter11,filter12;
    Bitmap myBitmap;
    PhotoFilter photoFilter;
    ImageView imageView,backtoHome , saveImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        imageView = findViewById(R.id.imageView);
        backtoHome = findViewById(R.id.imageBack);
        saveImage = findViewById(R.id.imageSave);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("imageUri");
        imageView.setImageURI(uri);

        Nofilter = findViewById(R.id.Nofilter);
        filter1 = findViewById(R.id.filter1);
        filter2 = findViewById(R.id.filter2);
        filter3 = findViewById(R.id.filter3);
        filter4 = findViewById(R.id.filter4);
        filter5 = findViewById(R.id.filter5);
        filter6 = findViewById(R.id.filter6);
        filter7 = findViewById(R.id.filter7);
        filter8 = findViewById(R.id.filter8);
        filter9 = findViewById(R.id.filter9);
        filter10 = findViewById(R.id.filter10);
        filter11 = findViewById(R.id.filter11);
        filter12 = findViewById(R.id.filter12);

            // Uri object is Converting to Bitmap obj
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        photoFilter = new PhotoFilter();
        // Button have Different Filter Implementation Acc. to  FilterLibrary
        Nofilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageURI(uri);
            }
        });
        filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setImageBitmap(photoFilter.two(getApplicationContext(),myBitmap));

            }
        });
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.three(getApplicationContext(),myBitmap));
            }
        });
        filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.four(getApplicationContext(),myBitmap));
            }
        });
        filter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.six(getApplicationContext(),myBitmap));
            }
        });
        filter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.eight(getApplicationContext(),myBitmap));
            }
        });
        filter6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.ten(getApplicationContext(),myBitmap));
            }
        });
        filter7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.eleven(getApplicationContext(),myBitmap));
            }
        });
        filter8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.twelve(getApplicationContext(),myBitmap));
            }
        });
        filter9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.thirteen(getApplicationContext(),myBitmap));
            }
        });
        filter10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.fourteen(getApplicationContext(),myBitmap));
            }
        });
        filter11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.fifteen(getApplicationContext(),myBitmap));
            }
        });
        filter12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(photoFilter.sixteen(getApplicationContext(),myBitmap));
            }
        });


        // back Button
        backtoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // save Button
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                saveImageToExternalStorage( UUID.randomUUID().toString() , bitmap);

                finish();
            }
        });

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

            Toast.makeText(FilterActivity.this,"saved Image Successfully" ,Toast.LENGTH_SHORT).show();

            return true;

        }
        catch (Exception e){

            Toast.makeText(this,"Image not saved: \n" + e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

        return false;


    }

}