package com.example.cardamagedetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
   EditText emailId,password,name,phone;
Button btnSignUp;
TextView tvsignin;
FirebaseDatabase rootNode;
DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        btnSignUp=findViewById(R.id.sinbutton);
        tvsignin=findViewById(R.id.Alregistered);
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String sname=name.getText().toString();
                String sphone=phone.getText().toString();
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                rootNode=FirebaseDatabase.getInstance();
                reference =rootNode.getReference("signup");
                UserHelperClass helperClass=new UserHelperClass(email,pass,sname,sphone);
                reference.child(sphone).setValue(helperClass);
                //reference.setValue(helperClass);
                if(email.isEmpty())
                {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(sphone.isEmpty())
                {
                    phone.setError("Please enter Phone Number");
                    phone.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {


                    reference.child(sphone).setValue(helperClass);
    startActivity(new Intent(MainActivity.this,LoginActivity.class));

                        }


    }
});
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }


        });
    }
}
