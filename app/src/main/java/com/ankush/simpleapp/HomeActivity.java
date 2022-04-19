package com.ankush.simpleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dev.ronnie.github.imagepicker.ImagePicker;
import dev.ronnie.github.imagepicker.ImageResult;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    ImagePicker imagePicker;
    View backtohome;
    Button selfie , gallary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imagePicker = new ImagePicker(HomeActivity.this);
        selfie = findViewById(R.id.selfie);
        gallary = findViewById(R.id.gallery);
        backtohome = findViewById(R.id.imageBack);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // this will handle Gallery and Camera Button
        selfie.setOnClickListener(v -> imagePicker.takeFromCamera(imageCallBack()));
        gallary.setOnClickListener(v -> imagePicker.pickFromStorage(imageCallBack()));



        List<items> itemsList = new ArrayList<>();

        // Str Getting Array of String from getAllshownImagePath() method and pass to List Array then to CustomAdapter
         ArrayList<String> str= getAllShownImagesPath(this);
        for (int i = 0; i < str.size(); i++) {
            itemsList.add(new items(str.get(i).toString(),str.get(i)));

        }

        GridView gridView = findViewById(R.id.grid_view);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_view, itemsList);
        gridView.setAdapter(customAdapter);

    }


    // Function used to Stored the Image into External Media
    private Function1<ImageResult<? extends Uri>, Unit> imageCallBack() {
        return imageResult -> {
            if (imageResult instanceof ImageResult.Success) {
                Uri uri = ((ImageResult.Success<Uri>) imageResult).getValue();

                  Intent intent = new Intent(HomeActivity.this,ImageSaved.class);
                intent.putExtra("DATA",uri.toString());
                startActivityForResult(intent,101);


            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                // Toast.makeText(MainActivity2.this, errorString, Toast.LENGTH_LONG).show();

            }
            return null;
        };
    }

    // This Function Returns Image Path from Media To Array<String>
    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index;
        StringTokenizer st1;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index);
            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
    }





}