package com.ankush.simpleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {
    ImageView imageView , backtohome;
    String result;
    Uri fileuri;

    Button added,cancel;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageView=findViewById(R.id.imageView);
        backtohome = findViewById(R.id.imageBack);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        readIntent();

        // Ucrop is OpenSource Library
        UCrop.Options options = new UCrop.Options();

        String dest_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.of(fileuri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(EditActivity.this);

        added = findViewById(R.id.saved);
        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                saveImageToExternalStorage( UUID.randomUUID().toString() , bitmap);

                finish();
            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
// Read Intent Pass by Activity
    private void readIntent() {
        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            result=intent.getStringExtra("DATA");
            fileuri = Uri.parse(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("RESULT",resultUri+"");
            setResult(-1,returnIntent);
            imageView.setImageURI(resultUri);
          //  finish();

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
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

            Toast.makeText(EditActivity.this,"saved Image Successfully" ,Toast.LENGTH_SHORT).show();

            return true;

        }
        catch (Exception e){

            Toast.makeText(this,"Image not saved: \n" + e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

        return false;


    }

}