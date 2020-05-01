package com.sumant.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatesActivity extends AppCompatActivity {

    TextView stateNameTextView , statesConfirmTextView , statesRecoveredTextView , statesDeathTextView ;
    private RecyclerView districtRV;
    String stateName;
    private RequestQueue requestQueue;
    private ArrayList<States> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        bindViews();
        getIntentData();
        setJsonData();
    }

    private void setJsonData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.covid19india.org/state_district_wise.json",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject state =response.getJSONObject(stateName);
                    JSONObject districtData=state.getJSONObject("districtData");
                    JSONArray keys=districtData.names();

                    for (int i = 0; i < keys.length(); ++i) {

                        String district = keys.getString(i);
                        String confirmed = districtData.getJSONObject(district).getString("confirmed");
                        String active = districtData.getJSONObject(district).getString("active");
                        String recovered = districtData.getJSONObject(district).getString("recovered");
                        String death = districtData.getJSONObject(district).getString("deceased");

                        States states = new States(district,confirmed,active,recovered,death);
                        arrayList.add( states );
                    }

                    DistrictAdapter districtAdapter = new DistrictAdapter(getApplicationContext(), arrayList);
                    districtRV.setAdapter( districtAdapter );

                }catch ( Exception e ){
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

    private void getIntentData() {
        stateName = getIntent().getStringExtra("state");
        stateNameTextView.setText( stateName );
        statesConfirmTextView.setText( getIntent().getStringExtra("confirm") );
        statesRecoveredTextView.setText( getIntent().getStringExtra("recovered") );
        statesDeathTextView.setText( getIntent().getStringExtra("death") );
    }

    private void bindViews() {
        stateNameTextView = findViewById(R.id.stateNameTextView);
        statesConfirmTextView = findViewById(R.id.statesConfirmedTextView);
        statesRecoveredTextView = findViewById(R.id.statesRecoveredTextView);
        statesDeathTextView = findViewById(R.id.statesDeathTextView);

        districtRV = findViewById(R.id.districtRV);
        districtRV.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
    }
}
