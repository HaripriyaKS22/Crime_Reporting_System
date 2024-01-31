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

public class User_send_found_report extends Activity implements JsonResponse
{
    Button b1;
    EditText e1,e2;
    ListView l1;
    public static String placename,details;
    public static String[] found_id,place,dtls,date,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_found_report);

        e1=(EditText)findViewById(R.id.etplace);
        e2=(EditText)findViewById(R.id.etdetails);
        l1=(ListView)findViewById(R.id.lvfound);
        b1=(Button)findViewById(R.id.btfound);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                placename=e1.getText().toString();
                details=e2.getText().toString();
                if(placename.equalsIgnoreCase(""))
                {
                    e1.setError("No value for Place");
                    e1.setFocusable(true);
                }
                else if(details.equalsIgnoreCase(""))
                {
                    e2.setError("No value for Details");
                    e2.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) User_send_found_report.this;
                    String q = "/user_send_found_report?loginid="+sh.getString("log_id","")+"&mp_ids="+sh.getString("fid","")+"&ftype="+sh.getString("ftype","")+"&placename="+placename+"&details="+details;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_send_found_report.this;
        String q = "/user_view_found_report?mp_ids="+User_view_missing_persons.mp_ids;
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
            if(method.equalsIgnoreCase("user_send_found_report")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Users_home.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),User_send_found_report.class));
                }
            }
            if(method.equalsIgnoreCase("user_view_found_report")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    found_id=new String[ja1.length()];
                    place=new String[ja1.length()];
                    dtls=new String[ja1.length()];
                    date=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        found_id[i]=ja1.getJSONObject(i).getString("found_id");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        dtls[i]=ja1.getJSONObject(i).getString("details");
                        date[i]=ja1.getJSONObject(i).getString("date_time");
                        value[i]="Place:  "+place[i]+"\nDetails:  "+dtls[i]+"\nDate:  "+date[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_text,value);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();

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
