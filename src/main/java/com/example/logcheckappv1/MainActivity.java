package com.example.logcheckappv1;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.29.144:5000/get_data";

    private String gbl_time = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        TableLayout table = new TableLayout(this);

        TableRow header = new TableRow(MainActivity.this);
        TextView tv = new TextView(MainActivity.this);
        tv.setPadding(10, 10, 50, 10);
        tv.setText("Vehicle Type");
        header.addView(tv);

        TextView tv1 = new TextView(MainActivity.this);
        tv1.setPadding(100, 10, 100, 10);
        tv1.setText("Time");
        header.addView(tv1);

        TextView tv2 = new TextView(MainActivity.this);
        tv2.setPadding(150, 10, 10, 10);
        tv2.setText("Log Type");
        header.addView(tv2);

        table.addView(header);
        mainLayout.addView(table);
        CountDownTimer t = new CountDownTimer( Long.MAX_VALUE , 1000) {

            // This is called every interval. (Every 10 seconds in this example)
            public void onTick(long millisUntilFinished) {
                getData();
            }

            public void onFinish() {
                Log.d("test","Timer last tick");
                start();
            }
        }.start();
    }

    private void getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String time = jsonObject.optString("time");
                    String type = jsonObject.optString("type");
                    if (!Objects.equals(gbl_time, time)) {
                        LinearLayout mainLayout = findViewById(R.id.mainLayout);
                        TableLayout table = new TableLayout(MainActivity.this);

                        TableRow header = new TableRow(MainActivity.this);
                        TextView tv = new TextView(MainActivity.this);
                        tv.setPadding(70, 10, 70, 10);
                        tv.setText(type);
                        header.addView(tv);

                        TextView tv1 = new TextView(MainActivity.this);
                        tv1.setPadding(50, 10, 50, 10);
                        tv1.setText(time);
                        header.addView(tv1);

                        TextView tv2 = new TextView(MainActivity.this);
                        tv2.setPadding(50, 10, 10, 10);
                        tv2.setText("ENTRY");
                        header.addView(tv2);

                        table.addView(header);
                        mainLayout.addView(table);
                        gbl_time = time;
                    }
//                    Toast.makeText(MainActivity.this, "GOT A LOG", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}