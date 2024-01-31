package com.example.peoplesvoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vehicle_home extends AppCompatActivity {

    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_home);

        startService(new Intent(getApplicationContext(), LocationService.class));

        b1=(Button)findViewById(R.id.btemergency);
        b2=(Button)findViewById(R.id.btcriminals);
        b3=(Button)findViewById(R.id.btlogout);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                startActivity(new Intent(getApplicationContext(),User_view_missing_persons.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                startActivity(new Intent(getApplicationContext(),User_view_wanted_criminals.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent b=new Intent(getApplicationContext(),Vehicle_home.class);
        startActivity(b);
    }
}