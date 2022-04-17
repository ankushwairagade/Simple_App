package com.ankush.simpleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {
    ImageView imageView;
    String result;
    Uri fileuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageView=findViewById(R.id.imageView);


        readIntent();

        UCrop.Options options = new UCrop.Options();


        String dest_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.of(fileuri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(EditActivity.this);
    }

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
}