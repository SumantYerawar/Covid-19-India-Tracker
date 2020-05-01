package com.sumant.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<States> arrayList = new ArrayList<>();
    private TextView confirmed , recovered , death, active, increasedConfirmTV, increaseActiveTV, increaseRecoverTV, increaseDeathTV, lastUpdate ;
    private RequestQueue requestQueue;
    private RecyclerView statesRV;
    private ImageView confirmImageView, deathImageView, recoverImageView, activeImageView;

    private int lastDate , currentDate , increasedConfirmInt , increasedActiveInt , increasedDeathInt , increasedRecoveredInt;
    private int confirmInteger , deathInteger , recoveredInteger , activeInteger ;

    private int currentMonth , lastMonth;

    private static long back_pressed;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupSharedPrefs();
        JsonRequestMethod();
    }

    private void setupSharedPrefs() {
        sharedPreferences = getApplicationContext().getSharedPreferences(AppConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private void bindViews() {
        confirmed = findViewById(R.id.confirmedTextView);
        recovered = findViewById(R.id.recoveredTextView);
        death = findViewById(R.id.deathTextView);
        active = findViewById(R.id.activeTextView);

        increasedConfirmTV = findViewById(R.id.increaseConfirmTV);
        increaseActiveTV = findViewById(R.id.increaseActiveTV);
        increaseDeathTV = findViewById(R.id.increaseDeathTV);
        increaseRecoverTV = findViewById(R.id.increaseRecoverTV);

        confirmImageView = findViewById(R.id.confirmImageView);
        deathImageView = findViewById(R.id.deathImageView);
        activeImageView = findViewById(R.id.activeImageView);
        recoverImageView = findViewById(R.id.recoverImageView);

        lastUpdate = findViewById(R.id.lastUpdate);

        statesRV = findViewById(R.id.statesRV);
        statesRV.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
    }

    private void JsonRequestMethod() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/data.json",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray stateWiseData = response.getJSONArray("statewise");
                    JSONObject todayAndTotalDataJsonObject = stateWiseData.getJSONObject(0);
                    String totalDeaths = todayAndTotalDataJsonObject.getString("deaths");
                    String totalRecover = todayAndTotalDataJsonObject.getString("recovered");
                    String totalConfirmed = todayAndTotalDataJsonObject.getString("confirmed");
                    String totalActive = todayAndTotalDataJsonObject.getString("active");
                    String dateHeader = todayAndTotalDataJsonObject.getString("lastupdatedtime").substring(0, 16);

                    confirmed.setText(totalConfirmed);
                    recovered.setText(totalRecover);
                    death.setText(totalDeaths);
                    active.setText(totalActive);
                    lastUpdate.setText("Last Update "+dateHeader.substring(0,5) + " " + dateHeader.substring(11,16));

                    String confirmSharedPref = sharedPreferences.getString(AppConstant.CONFIRMED,null);
                    String activeSharedPref = sharedPreferences.getString(AppConstant.ACTIVE,null);
                    String deathSharedPref = sharedPreferences.getString(AppConstant.DEATH,null);
                    String recoverSharedPref = sharedPreferences.getString(AppConstant.RECOVERED,null);
                    String dateSharedPref = sharedPreferences.getString(AppConstant.UPDATED_DATE,null);
                    String monthSharedPref = sharedPreferences.getString(AppConstant.UPDATED_MONTH,null);

                    if ( confirmSharedPref == null || activeSharedPref == null ||
                            deathSharedPref == null ||  recoverSharedPref == null || dateSharedPref == null || monthSharedPref == null || currentDate > lastDate || currentMonth > lastMonth ){
                        setData( totalConfirmed , totalDeaths , totalRecover , totalActive , dateHeader.substring(0,2) , dateHeader.substring(3,5));
                    }else {
                        increasedConfirmInt = Integer.parseInt( confirmSharedPref );
                        increasedActiveInt = Integer.parseInt( activeSharedPref );
                        increasedDeathInt = Integer.parseInt( deathSharedPref );
                        increasedRecoveredInt = Integer.parseInt( recoverSharedPref );
                        lastDate = Integer.parseInt( dateSharedPref );
                        lastMonth = Integer.parseInt( monthSharedPref );

                        confirmInteger = Integer.parseInt( totalConfirmed );
                        deathInteger = Integer.parseInt( totalDeaths );
                        recoveredInteger = Integer.parseInt( totalRecover );
                        activeInteger = Integer.parseInt( totalActive );
                        currentDate = Integer.parseInt( dateHeader.substring(0,2) );
                        currentMonth = Integer.parseInt( dateHeader.substring(3,5) );
                    }


                    if ( recoveredInteger >= increasedRecoveredInt ) {

                        increaseRecoverTV.setText( String.valueOf( recoveredInteger - increasedRecoveredInt ) );
                        recoverImageView.setImageResource(R.drawable.ic_arrow_upward1);
                        DrawableCompat.setTint(recoverImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.green));

                    }else if ( recoveredInteger < increasedRecoveredInt ){

                        increaseRecoverTV.setText( String.valueOf( increasedRecoveredInt - recoveredInteger ) );
                        recoverImageView.setImageResource(R.drawable.ic_arrow_downward1);
                        DrawableCompat.setTint(recoverImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.green ));

                    }

                    if ( confirmInteger >= increasedConfirmInt ){

                        increasedConfirmTV.setText( String.valueOf( confirmInteger - increasedConfirmInt ) );
                        confirmImageView.setImageResource(R.drawable.ic_arrow_upward2);
                        DrawableCompat.setTint(confirmImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.red ));

                    }else if ( confirmInteger < increasedConfirmInt ){

                        confirmImageView.setImageResource(R.drawable.ic_arrow_downward);
                        increasedConfirmTV.setText( String.valueOf( increasedConfirmInt - confirmInteger ) );
                        DrawableCompat.setTint(confirmImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.red ));

                    }

                    if ( activeInteger >= increasedActiveInt ){

                        increaseActiveTV.setText( String.valueOf( activeInteger - increasedActiveInt ) );
                        activeImageView.setImageResource(R.drawable.ic_arrow_upward3);
                        DrawableCompat.setTint(activeImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.blue ));

                    }else if ( activeInteger < increasedActiveInt ){

                        activeImageView.setImageResource(R.drawable.ic_arrow_downward);
                        increaseActiveTV.setText( String.valueOf( increasedActiveInt - activeInteger ) );
                        DrawableCompat.setTint(activeImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.blue ));

                    }

                    if ( deathInteger >= increasedDeathInt ){

                        increaseDeathTV.setText( String.valueOf( deathInteger - increasedDeathInt ) );
                        deathImageView.setImageResource(R.drawable.ic_arrow_upward);
                        DrawableCompat.setTint(deathImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.gray ));

                    }else if ( deathInteger < increasedDeathInt ){

                        deathImageView.setImageResource(R.drawable.ic_arrow_downward);
                        increaseDeathTV.setText( String.valueOf( increasedDeathInt - deathInteger ) );
                        DrawableCompat.setTint(deathImageView.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.gray ));

                    }

                    for ( int i = 1 ; i < stateWiseData.length() ; i++ ){
                        JSONObject stateName = stateWiseData.getJSONObject(i);
                        String state = stateName.getString("state");
                        String confirm = stateName.getString("confirmed");
                        String active = stateName.getString("active");
                        String recover = stateName.getString("recovered");
                        String death = stateName.getString("deaths");
                        States states = new States(state,confirm,active,recover,death);
                        arrayList.add( states);
                    }

                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, arrayList);
                    statesRV.setAdapter( recyclerViewAdapter );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setData(String totalConfirmed, String totalDeaths, String totalRecover, String totalActive, String dateSubstring, String monthSubstring) {

        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putString(AppConstant.CONFIRMED, totalConfirmed);
        editor1.putString(AppConstant.DEATH, totalDeaths);
        editor1.putString(AppConstant.RECOVERED, totalRecover);
        editor1.putString(AppConstant.ACTIVE, totalActive);
        editor1.putString(AppConstant.UPDATED_DATE, dateSubstring);
        editor1.putString(AppConstant.UPDATED_MONTH, monthSubstring);
        editor1.commit();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
