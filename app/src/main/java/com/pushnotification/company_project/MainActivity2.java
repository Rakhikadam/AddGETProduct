package com.pushnotification.company_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TextView Title,Price,Category,Decription,Rate,Count;
    ImageView Image;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Title = findViewById(R.id.title);
        Price = findViewById(R.id.price);
        Category = findViewById(R.id.category);
        Decription = findViewById(R.id.decription);
        Image = findViewById(R.id.image);
        send = findViewById(R.id.button1);
        Rate = findViewById(R.id.rate);
        Count = findViewById(R.id.count);
       // ProductAdpter adpter = new ProductAdpter();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recievedata();
            }
        });


    }
    private  void  recievedata(){
       

       JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
               "https://fakestoreapi.com/products", null,

               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                   //    JSONObject data = new JSONObject();

                       try {
                           JSONObject object1  =  response; //only one objec. call response
                          // JSONObject object1=  response.getJSONObject(0);
                           String title =  object1.getString("title");
                          Double price =  object1.getDouble("price");
                            String description =  object1.getString("description");
                            String category =  object1.getString("category");
                            String image =  object1.getString("image");

                            JSONObject data = response.getJSONObject("rating");
                            String rate = data.optString("rate","NA");
                            String count = data.optString("count","NA");

                            Title.setText(title);
                          Price.setText(price.toString());
                            Decription.setText(description);
                            Category.setText(category);
                            Count.setText(count);
                            Rate.setText(rate);
                           Glide.with(MainActivity2.this).load(image).into(Image);

                       } catch (JSONException e) {
                           throw new RuntimeException(e);
                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Log.e("TAG", "onErrorResponse: "+error.getMessage() );

                   }
               }
       );
        Volley.newRequestQueue(MainActivity2.this).add(objectRequest);

    }


}