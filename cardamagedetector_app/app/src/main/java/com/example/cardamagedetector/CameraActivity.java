package com.example.cardamagedetector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    String currentImagePath=null;
    //private static final int IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ImageView shortcut;
        shortcut=findViewById(R.id.shortcut);
        Button dispop=findViewById(R.id.dispop);
        dispop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(CameraActivity.this,spotdamage.class);
                startActivity(intsignup);
            }
        });
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(CameraActivity.this,HomeActivity.class);
                startActivity(intsignup);
            }
        });
    }


           public void captureImage(View view) {
                Intent cameraIntent;
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null)
                {
                    File imageFile=null;
                    try {
                        imageFile=getImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(imageFile!=null)
                    {
                        Uri imageUri= FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivity(cameraIntent);
                    }
                }
            }

            public void displayImage(View view) {
                Intent intent=new Intent(this,Displayimage.class);
                intent.putExtra("image_path",currentImagePath);
                startActivity(intent);
            }
            private File getImageFile() throws IOException {
                @SuppressLint("SimpleDateFormat") String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageName="jpg_"+timeStamp+"_";
                File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File imageFile=File.createTempFile(imageName,".jpg",storageDir);
                currentImagePath=imageFile.getAbsolutePath();
                return imageFile;

            }
        }


