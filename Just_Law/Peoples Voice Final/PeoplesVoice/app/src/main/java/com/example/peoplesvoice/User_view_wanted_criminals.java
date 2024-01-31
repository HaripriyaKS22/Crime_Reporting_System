package com.example.peoplesvoice;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_wanted_criminals extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] crm_id,cname,hname,place,pincode,age,gender,stat,most_wanted,image,cat,des;
    public static String crm_ids;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_wanted_criminals);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv1=(ListView)findViewById(R.id.lvcrminals);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_wanted_criminals.this;
        String q = "/user_view_wanted_criminals";
        q=q.replace(" ","%20");
        JR.execute(q);
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_wanted_criminals")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    crm_id=new String[ja1.length()];
                    cname=new String[ja1.length()];
                    hname=new String[ja1.length()];
                    place=new String[ja1.length()];
                    pincode=new String[ja1.length()];
                    age=new String[ja1.length()];
                    gender=new String[ja1.length()];
                    stat=new String[ja1.length()];
                    most_wanted=new String[ja1.length()];
                    cat=new String[ja1.length()];
                    des=new String[ja1.length()];

                    image=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        crm_id[i]=ja1.getJSONObject(i).getString("criminal_id");
                        cname[i]=ja1.getJSONObject(i).getString("cname");
                        hname[i]=ja1.getJSONObject(i).getString("house_name");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        pincode[i]=ja1.getJSONObject(i).getString("pincode");
                        age[i]=ja1.getJSONObject(i).getString("age");
                        gender[i]=ja1.getJSONObject(i).getString("gender");
                        stat[i]=ja1.getJSONObject(i).getString("status");
                        most_wanted[i]=ja1.getJSONObject(i).getString("most_wanted");
                        cat[i]=ja1.getJSONObject(i).getString("category_name");
                        des[i]=ja1.getJSONObject(i).getString("description");
                        image[i]=ja1.getJSONObject(i).getString("photo");



                    }
                    Custimage_wanted clist=new Custimage_wanted(this,image,cname,age,gender,stat,most_wanted,cat,des);
                    lv1.setAdapter(clist);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("user_share_criminal_details"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Sahred!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
//				}
//			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        crm_ids=crm_id[arg2];
        SharedPreferences.Editor e=sh.edit();
        e.putString("fid", crm_ids);
        e.putString("ftype", "mcriminal");
        e.commit();

        final CharSequence[] items = {"Share","Found","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_wanted_criminals.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Share"))
                {
                    String q="Criminal Name : "+cname[arg2]+"\nHouse Name : "+hname[arg2]+"\nPlace : "+place[arg2]+"\nAge : "+age[arg2]+"\nGender : "+gender[arg2]+"\nMost Wanted : "+most_wanted[arg2]+"\nCategory : "+cat[arg2]+"\nDescription : "+des[arg2];
//                    String q = "http://" + sh.getString("ip", "") + "/user_share_criminal_details?crm_ids="+crm_ids;
//                    q=q.replace(" ","%20");
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_TEXT, URL_TO_SHARE);
//                    startActivity(Intent.createChooser(intent, q));

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, q);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
                else if (items[item].equals("Found"))
                {
                    startActivity(new Intent(getApplicationContext(),User_send_found_report.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }

    @Override


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();

        if(sh.getString("usertype","")=="vehicle"){

            Intent b=new Intent(getApplicationContext(),Vehicle_home.class);
            startActivity(b);


        }
        else  if(sh.getString("usertype","")=="user"){

            Intent b=new Intent(getApplicationContext(),Users_home.class);
            startActivity(b);


        }
    }



}
