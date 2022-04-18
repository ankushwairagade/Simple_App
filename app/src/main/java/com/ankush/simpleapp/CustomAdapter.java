package com.ankush.simpleapp;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<items> {
    private static final String TAG = "Custoo";
    List<items> items_list = new ArrayList<>();
    int custom_layout_id;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<items> objects) {
        super(context, resource, objects);
        items_list = objects;
        custom_layout_id = resource;
    }

    @Override
    public int getCount() {
        return items_list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            // getting reference to the main layout and
            // initializing
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(custom_layout_id, null);
        }

        // initializing the imageview and textview and
        // setting data
        ImageView imageView = v.findViewById(R.id.imageView);
        TextView textView = v.findViewById(R.id.textView);

        // get the item using the position param
        items item = items_list.get(position);

        //imageView.setImageResource(item.getImage_id());


        Uri uri = Uri.parse(item.getImage_id());

        //Log.i(TAG,"checko"+ uri);
        imageView.setImageURI(uri);

        textView.setText(item.getText());
        return v;
    }

}
