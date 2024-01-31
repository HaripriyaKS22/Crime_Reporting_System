package com.example.peoplesvoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Useraddemergency extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1;
    Button b1;
    ListView l1;
    String[] em,date,lati,longi,sts,value;
    String emergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraddemergency);

        e1=(EditText) findViewById(R.id.ev12);
        b1=(Button) findViewById(R.id.button1);
        l1=(ListView) findViewById(R.id.lvem);

        l1.setOnItemClickListener(this);
        startService(new Intent(getApplicationContext(),LocationService.class));
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Useraddemergency.this;
        String q = "/viewemergency?login="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emergency=e1.getText().toString();
                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Useraddemergency.this;
                String q = "/sendemergency?login="+Login.logid+"&emergency="+emergency+"&lati="+LocationService.lati+"&logi="+LocationService.logi;
                q=q.replace(" ","%20");
                JR.execute(q);
                Toast.makeText(getApplicationContext(), "Emergency updated...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Useraddemergency.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                em = new String[ja1.length()];
                lati = new String[ja1.length()];
                longi = new String[ja1.length()];
                date = new String[ja1.length()];
                sts = new String[ja1.length()];
//                licence = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    lati[i] = ja1.getJSONObject(i).getString("latitude");
                    longi[i] = ja1.getJSONObject(i).getString("longitude");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    sts[i] = ja1.getJSONObject(i).getString("status");
                    em[i] = ja1.getJSONObject(i).getString("emergency");
//                    agentname[i] = ja1.getJSONObject(i).getString("agent_name");
                    value[i] = "Emergency: " + em[i] + "\nDate: " + date[i] + "\nLatitude: " + lati[i] + "\nLongitude: " + longi[i]+ "\nStatus: " + sts[i]+"\n"+"\n";

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }
}