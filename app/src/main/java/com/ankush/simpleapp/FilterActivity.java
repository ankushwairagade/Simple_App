package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

public class FilterActivity extends AppCompatActivity {

    ImageView imagePreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        imagePreview = findViewById(R.id.imageView);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("imageUri");
       // Toast.makeText(this,uri,Toast.LENGTH_LONG).show();
        imagePreview.setImageURI(uri);



    }
}