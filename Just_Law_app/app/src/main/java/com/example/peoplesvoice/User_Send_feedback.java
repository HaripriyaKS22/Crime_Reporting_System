package com.example.peoplesvoice;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class User_Send_feedback extends Activity implements JsonResponse
{
	Button b1;
	EditText e1;
	ListView l1;
	public static String feedback_des;
	public static String[] feedback_id,feed_des,reply,date,value,artist_name;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user__send_feedback);
		
		e1=(EditText)findViewById(R.id.etplace);
		l1=(ListView)findViewById(R.id.lvfeedbacklist);
		b1=(Button)findViewById(R.id.btsendfeedback);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				feedback_des=e1.getText().toString();
				if(feedback_des.equalsIgnoreCase(""))
				{
				  e1.setError("No value for feedback");
				  e1.setFocusable(true);
				}
				else{
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) User_Send_feedback.this;
		        String q = "/usersendfeedback?loginid="+Login.logid+"&feed_des="+feedback_des;
		        q=q.replace(" ","%20");
		        JR.execute(q);
				}
			}
		});
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_Send_feedback.this;
        String q = "/userviewfeedback?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("usersendfeedback")){
				String status=jo.getString("status");
				Log.d("pearl",status);
				//Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
				if(status.equalsIgnoreCase("success")){
				
					Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
				    startActivity(new Intent(getApplicationContext(),User_Send_feedback.class));
			}
				else
				{
					Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Users_home.class));
				}
			}
			if(method.equalsIgnoreCase("userviewfeedback")){
				String status=jo.getString("status");
				Log.d("pearl",status);
				
				
				if(status.equalsIgnoreCase("success")){
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					//feedback_id=new String[ja1.length()];
					 feed_des=new String[ja1.length()];
					 date=new String[ja1.length()];
					 value=new String[ja1.length()];
					  
						for(int i = 0;i<ja1.length();i++)
						{ 
							//feedback_id[i]=ja1.getJSONObject(i).getString("feedback_id");
							feed_des[i]=ja1.getJSONObject(i).getString("feedback");
							date[i]=ja1.getJSONObject(i).getString("date_time");
							value[i]="Description:  "+feed_des[i]+"\nDate:  "+date[i];
							
						
						}
						ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_text,value);
						l1.setAdapter(ar);
						//startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));	 
				}
				
				else
					
				{    				 
					Toast.makeText(getApplicationContext(), "No feedbacks!!", Toast.LENGTH_LONG).show();
					
				}
			}
				
		}catch(Exception e)
		{  
		   Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	
		
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Users_home.class);			
		startActivity(b);
	}

}
