package com.example.cardamagedetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView loc,cam,details,abt;
        loc=findViewById(R.id.loc);
        cam=findViewById(R.id.cam);
        details=findViewById(R.id.details);
        abt=findViewById(R.id.abt);
        abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(HomeActivity.this,Abtapp.class);
                startActivity(intsignup);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(HomeActivity.this,CarDetails.class);
                startActivity(intsignup);
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(HomeActivity.this,LocationActivity.class);
                startActivity(intsignup);
            }
    });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(HomeActivity.this,CameraActivity.class);
                startActivity(intsignup);
            }
        });
}

}

