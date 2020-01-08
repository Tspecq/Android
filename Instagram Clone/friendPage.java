package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class friendPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_page);
        final LinearLayout layout = findViewById(R.id.page);

        Log.i("Info","Get intent");
        Intent intent = getIntent();
        String activeUsername = intent.getStringExtra("username");

        setTitle(activeUsername+" feed");
        Log.i("Info","Start query");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username",activeUsername);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){
                    Log.i("Info","No error query");
                    if(objects.size() > 0){
                        Log.i("Info","Object query > 0");

                        for (ParseObject object : objects){

                            ParseFile file = (ParseFile) object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null && data != null){
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());
                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));
                                        imageView.setImageBitmap(bitmap);
                                        Log.i("Info","Ok till now");
                                        layout.addView(imageView);
                                    }
                                }
                            });
                        }
                    }

                }
            }
        });


         }
}

