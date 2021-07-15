package com.example.cardamagedetector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class Displayimage extends AppCompatActivity {

    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayimage);
                  imageview =findViewById(R.id.mimageView);
                Bitmap bitmap= BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
                imageview.setImageBitmap(bitmap);
            }
        }
