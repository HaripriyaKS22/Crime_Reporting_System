package com.example.peoplesvoice;

import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_complaints extends Activity implements JsonResponse {
    ListView lv1;
    String [] title,desp,date,stat,image;
    public static String mp_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_complaints);
        lv1=(ListView)findViewById(R.id.lvcmp);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_complaints.this;
        String q = "/user_view_complaints?login_id="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_complaints")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    title=new String[ja1.length()];
                    desp=new String[ja1.length()];
                    date=new String[ja1.length()];
                    stat=new String[ja1.length()];
                    image=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        title[i]=ja1.getJSONObject(i).getString("title");
                        desp[i]=ja1.getJSONObject(i).getString("description");
                        date[i]=ja1.getJSONObject(i).getString("date_time");
                        stat[i]=ja1.getJSONObject(i).getString("status");
                        image[i]=ja1.getJSONObject(i).getString("file");



                    }
                    Custimage_complaints clist=new Custimage_complaints(this,image,title,desp,date,stat);
                    lv1.setAdapter(clist);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }



    @Override

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }




}
