package com.example.cardamagedetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText phone,password;
    Button btnSignIn;
    TextView tvsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.Pass);
        btnSignIn=findViewById(R.id.Sinbutton);
        tvsignup=findViewById(R.id.Notregistered);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneno;
                phoneno = phone.getText().toString();
                final String pass = password.getText().toString();
                if (phoneno.isEmpty()) {
                    phone.setError("Please enter Phoneno");
                    phone.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else if (phoneno.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();

                }
                else if (!(phoneno.isEmpty() && pass.isEmpty())) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("signup");
                          Query checkUser = reference.orderByChild("phoneno").equalTo(phoneno);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override


                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                String passwordfromdb = snapshot.child(phoneno).child("password").getValue(String.class);

if (passwordfromdb.equals(pass)) {

                                    String emailfromdb = snapshot.child(phoneno).child("email").getValue(String.class);
                                    String phonefromdb = snapshot.child(phoneno).child("phoneno").getValue(String.class);
                                    String namefromdb = snapshot.child(phoneno).child("name").getValue(String.class);
                                    Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                    // startActivity(intToHome);
                                    intToHome.putExtra("email", emailfromdb);
                                    intToHome.putExtra("phoneno", phonefromdb);
                                    intToHome.putExtra("name", namefromdb);
                                    intToHome.putExtra("password", passwordfromdb);
                                    startActivity(intToHome);
                                } else {
                                    password.setError("Wrong Password");
                                    password.requestFocus();
                                }
                            } else {
                                phone.setError("No such user exists");
                                phone.requestFocus();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

                tvsignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intsignup=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intsignup);
                    }
        });

    }
}

