package com.ankush.simpleapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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

    ActivityResultLauncher<String> mGetContent;
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
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                imagePicker.pickFromStorage(imageCallFilter());

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetContent.launch("image/*");

            }
        });
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("DATA",result.toString());
                startActivityForResult(intent,101);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==-1 && requestCode==101)
        {
            String result=data.getStringExtra("RESULT");
            Uri resUri = null;

            if(result!=null)
            {
                resUri=Uri.parse(result);
            }

            //img.setImageURI(resUri);
            // this is how we get img back from Lib() if you wish then stored in photos other for now
            // dont do any thing !
        }
    }

}