package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dev.ronnie.github.imagepicker.ImagePicker;
import dev.ronnie.github.imagepicker.ImageResult;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    ImagePicker imagePicker;
    Button selfie , gallary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imagePicker = new ImagePicker(HomeActivity.this);

        selfie = findViewById(R.id.selfie);
        gallary = findViewById(R.id.gallery);

        selfie.setOnClickListener(v -> imagePicker.takeFromCamera(imageCallBack()));
        gallary.setOnClickListener(v -> imagePicker.pickFromStorage(imageCallBack()));

    }


    private Function1<ImageResult<? extends Uri>, Unit> imageCallBack() {
        return imageResult -> {
            if (imageResult instanceof ImageResult.Success) {
                Uri uri = ((ImageResult.Success<Uri>) imageResult).getValue();
                // Error when too big img come   so applying img lowResolution
               // img.setImageURI(uri);
            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                // Toast.makeText(MainActivity2.this, errorString, Toast.LENGTH_LONG).show();
            }
            return null;
        };
    }
}