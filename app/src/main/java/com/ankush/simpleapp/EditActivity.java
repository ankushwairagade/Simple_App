package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class EditActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageView=findViewById(R.id.imageView);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("imageUri");
        // Toast.makeText(this,uri,Toast.LENGTH_LONG).show();
        imageView.setImageURI(uri);
    }
}