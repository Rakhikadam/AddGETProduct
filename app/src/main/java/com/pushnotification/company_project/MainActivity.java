package com.pushnotification.company_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button save, submit;
    EditText title, price, description, category;
    EditText image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save = findViewById(R.id.button);
        Button button = findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog customdialog = new Dialog(MainActivity.this);
                customdialog.setContentView(R.layout.adddata);
                title = customdialog.findViewById(R.id.ftext1);
                price = customdialog.findViewById(R.id.ftext3);
                description = customdialog.findViewById(R.id.ftext4);
                category = customdialog.findViewById(R.id.ftext5);
                image = customdialog.findViewById(R.id.ftext2);
                submit = customdialog.findViewById(R.id.fsubmit);
                customdialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        senddetails(customdialog);
                    }
                });

                //  senddetails();
            }
        });



    }

    private void senddetails(Dialog customdialog) {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("title", title.getText().toString());
            object.put("price", price.getText().toString());
            object.put("description", description.getText().toString());
            object.put("image", image.getText().toString());
            object.put("category", category.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "https://fakestoreapi.com/products/", object,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.e("TAG", "onResponse: " + response.toString());
                        customdialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        customdialog.dismiss();
                    }
                }
        );


        Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);

//        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//        queue.add(jsonObjectRequest);

    }
}