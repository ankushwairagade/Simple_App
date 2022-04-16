package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dev.ronnie.github.imagepicker.ImagePicker;
import dev.ronnie.github.imagepicker.ImageResult;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    ImagePicker imagePicker;

    Button home,edit,filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePicker = new ImagePicker(MainActivity.this);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.home);
        edit = findViewById(R.id.edit);
        filter =findViewById(R.id.filter);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagePicker.pickFromStorage(imageCallEdit());

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                imagePicker.pickFromStorage(imageCallFilter());

            }
        });
    }


    private Function1<ImageResult<? extends Uri>, Unit> imageCallFilter() {
        return imageResult -> {
            if (imageResult instanceof ImageResult.Success) {
                Uri uri = ((ImageResult.Success<Uri>) imageResult).getValue();
                // Error when too big img come   so applying img lowResolution
                // img.setImageURI(uri);
                Intent intent = new Intent(MainActivity.this,FilterActivity.class);
                intent.putExtra("imageUri", uri);

                startActivity(intent);

            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                 Toast.makeText(MainActivity.this, errorString, Toast.LENGTH_LONG).show();
            }
            return null;
        };
    }

    private Function1<ImageResult<? extends Uri>, Unit> imageCallEdit() {
        return imageResult -> {
            if (imageResult instanceof ImageResult.Success) {
                Uri uri = ((ImageResult.Success<Uri>) imageResult).getValue();
                // Error when too big img come   so applying img lowResolution
                // img.setImageURI(uri);
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("imageUri", uri);

                startActivity(intent);

            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                Toast.makeText(MainActivity.this, errorString, Toast.LENGTH_LONG).show();
            }
            return null;
        };
    }
}