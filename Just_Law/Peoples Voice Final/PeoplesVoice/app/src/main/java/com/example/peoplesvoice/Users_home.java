package com.example.peoplesvoice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Users_home extends Activity {
	
	Button b1,b2,b3,b4,b5,b6,b7,b8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_home);
		
		b1=(Button)findViewById(R.id.btfeed);
		b2=(Button)findViewById(R.id.btemc);
		b3=(Button)findViewById(R.id.btmissing);
		b4=(Button)findViewById(R.id.btcriminals);
		b5=(Button)findViewById(R.id.btcomplaints);
//		b6=(Button)findViewById(R.id.btnearby);
		b7=(Button)findViewById(R.id.btlogout);
		b8=(Button)findViewById(R.id.EMA);

		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_Send_feedback.class));
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_emergecy_contact.class));
			}
		});

		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_missing_persons.class));
			}
		});

		b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_wanted_criminals.class));
			}
		});

		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_send_complaints.class));
			}
		});
//
//		b6.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
//				startActivity(new Intent(getApplicationContext(),User_view_nearby_places.class));
//			}
//		});
		
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
		});
		b8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(),Useraddemergency.class));
			}
		});
	}


	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent b=new Intent(getApplicationContext(),Users_home.class);
		startActivity(b);
	}

}
