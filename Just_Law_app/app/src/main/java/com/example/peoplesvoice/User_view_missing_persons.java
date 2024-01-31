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

public class User_view_missing_persons extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] mp_id,pname,hname,place,pincode,cperson,relation,phone,image;
    public static String mp_ids;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_missing_persons);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv1=(ListView)findViewById(R.id.lvmissing);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_missing_persons.this;
        String q = "/user_view_missing_persons";
        q=q.replace(" ","%20");
        JR.execute(q);
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_missing_persons")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    mp_id=new String[ja1.length()];
                    pname=new String[ja1.length()];
                    hname=new String[ja1.length()];
                    place=new String[ja1.length()];
                    pincode=new String[ja1.length()];
                    cperson=new String[ja1.length()];
                    relation=new String[ja1.length()];
                    phone=new String[ja1.length()];

                    image=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        mp_id[i]=ja1.getJSONObject(i).getString("missing_person_id");
                        pname[i]=ja1.getJSONObject(i).getString("pname");
                        hname[i]=ja1.getJSONObject(i).getString("house_name");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        pincode[i]=ja1.getJSONObject(i).getString("pincode");
                        cperson[i]=ja1.getJSONObject(i).getString("contact_person");
                        relation[i]=ja1.getJSONObject(i).getString("relation");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        image[i]=ja1.getJSONObject(i).getString("photo");



                    }
                    Custimage clist=new Custimage(this,image,pname,hname,place,pincode,cperson,relation);
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
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        mp_ids=mp_id[arg2];

        SharedPreferences.Editor e=sh.edit();
        e.putString("fid", mp_ids);
            e.putString("ftype", "mperson");
        e.commit();


        final CharSequence[] items = {"share","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_missing_persons.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("share"))
                {
                    String q="Criminal Name : "+pname[arg2]+"\nHouse Name : "+hname[arg2]+"\nPlace : "+place[arg2]+"\ncontact person : "+cperson[arg2]+"\nphone : "+phone[arg2];
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
