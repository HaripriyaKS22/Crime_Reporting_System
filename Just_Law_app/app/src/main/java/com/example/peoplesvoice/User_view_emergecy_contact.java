package com.example.peoplesvoice;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_emergecy_contact extends Activity implements JsonResponse {
    ListView lv1;
    String [] contact_id,authority,details,number,val;
    public static String i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_emergecy_contact);

        lv1=(ListView)findViewById(R.id.lvem);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_emergecy_contact.this;
        String q = "/user_view_em_contact";
        q=q.replace(" ","%20");
        JR.execute(q);
    }





    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_em_contact")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    contact_id=new String[ja1.length()];
                    authority=new String[ja1.length()];
                    details=new String[ja1.length()];
                    number=new String[ja1.length()];


                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        contact_id[i]=ja1.getJSONObject(i).getString("contact_id");
                        authority[i]=ja1.getJSONObject(i).getString("authority");
                        details[i]=ja1.getJSONObject(i).getString("details");
                        number[i]=ja1.getJSONObject(i).getString("number");


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Authority : "+authority[i]+" - "+number[i]+"\nDetails"+details[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_text,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
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
