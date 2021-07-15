package com.example.cardamagedetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        EditText type,year,model;
        Button next;
        next=findViewById(R.id.nextbutton);
        ImageView shortcut;
        shortcut=findViewById(R.id.shortcut);
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(CarDetails.this,HomeActivity.class);
                startActivity(intsignup);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup=new Intent(CarDetails.this,CameraActivity.class);
                startActivity(intsignup);
            }
        });
        type=findViewById(R.id.type);
        year=findViewById(R.id.year);
        model=findViewById(R.id.model);
        String value1=type.getText().toString();
        String value2=year.getText().toString();
        String value3=model.getText().toString();
       /* Intent intent=new Intent(getApplicationContext(),DisplayActivity.class);
        intent.putExtra("Value1",value1);
        intent.putExtra("value2",value2);*/
        final TextView post_response_text=findViewById(R.id.response_data);
        String URL="http:/srpmadhu.pythonanywhere.com/predict";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();

        try {
            //input your API parameters
            object.put("path","C:/Users/sundar/Downloads/car-damage-dataset/data1a/training/00-damage/0047.jpeg");
            object.put("MAKE",value1);
            object.put("YEAR",value2);
            object.put("MODEL",value3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        post_response_text.setText("Response :" +response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error", "Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
