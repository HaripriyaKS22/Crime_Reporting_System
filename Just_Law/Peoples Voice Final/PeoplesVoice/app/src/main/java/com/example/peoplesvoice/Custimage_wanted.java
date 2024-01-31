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

public class Custimage_wanted extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] image,cname,age,gender,stat,most_wanted,cat,des;

    public Custimage_wanted(Activity context, String[] image, String[] cname,  String[] age, String[] gender, String[] stat, String[] most_wanted, String[] cat, String[] des) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.custimage_wanted, image);
        this.context = context;
        this.image = image;
        this.cname = cname;

        this.age = age;
        this.gender = gender;
        this.stat = stat;
        this.most_wanted = most_wanted;
        this.cat = cat;
        this.des = des;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custimage_wanted, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
        TextView t1 = (TextView) listViewItem.findViewById(R.id.tvcname);
//        TextView t2 = (TextView) listViewItem.findViewById(R.id.tvhname);
//        TextView t3 = (TextView) listViewItem.findViewById(R.id.tvplace);
//        TextView t4 = (TextView) listViewItem.findViewById(R.id.tvpincode);
        TextView t5 = (TextView) listViewItem.findViewById(R.id.tvage);
        TextView t6 = (TextView) listViewItem.findViewById(R.id.tvgender);
        TextView t7 = (TextView) listViewItem.findViewById(R.id.tvstat);
        TextView t8 = (TextView) listViewItem.findViewById(R.id.tvmost_wanted);
        TextView t9 = (TextView) listViewItem.findViewById(R.id.tvcat);
        TextView t10 = (TextView) listViewItem.findViewById(R.id.tvdes);

        t1.setText("Criminal Name : " + cname[position]);
//        t2.setText("House Name : " + hname[position]);
//        t3.setText("Place : " + place[position]);
//        t4.setText("Pincode : " + pincode[position]);
        t5.setText("Age : " + age[position]);
        t6.setText("Gender : " + gender[position]);
        t7.setText("Status : " + stat[position]);
        t8.setText("Wanted : " + most_wanted[position]);
        t9.setText("Crime Category : " + cat[position]);
        t10.setText("Crime Description : " + des[position]);


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