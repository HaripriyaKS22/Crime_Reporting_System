package com.example.peoplesvoice;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custimage_complaints extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] image,title,desp,date,stat;

    public Custimage_complaints(Activity context, String[] image, String[] title, String[] desp, String[] date, String[] stat) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.custimage_complaints, image);
        this.context = context;
        this.image = image;
        this.title = title;
        this.desp = desp;
        this.date = date;
        this.stat = stat;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custimage_complaints, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
        TextView t1 = (TextView) listViewItem.findViewById(R.id.tvtitle);
        TextView t2 = (TextView) listViewItem.findViewById(R.id.tvdesp);
        TextView t3 = (TextView) listViewItem.findViewById(R.id.tvdate);
        TextView t4 = (TextView) listViewItem.findViewById(R.id.tvstat);

        t1.setText("Title : " + title[position]);
        t2.setText("Despriction : " + desp[position]);
        t3.setText("Date Time : " + date[position]);
        t4.setText("Status : " + stat[position]);

        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(im);

        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}