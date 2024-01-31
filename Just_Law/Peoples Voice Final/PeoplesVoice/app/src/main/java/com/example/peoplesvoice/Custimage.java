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

public class Custimage extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[]image,pname,hname,place,pincode,cperson,relation;
	
	 public Custimage(Activity context, String[] image, String[] pname, String[] hname, String[] place, String[] pincode, String[] cperson, String[] relation) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images, image);
	        this.context = context;
	        this.image = image;
		 	this.pname = pname;
		 	this.hname = hname;
		 	this.place = place;
		 	this.pincode = pincode;
		 	this.cperson = cperson;
			this.relation = relation;

	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.tvpname);
			TextView t2=(TextView)listViewItem.findViewById(R.id.tvhname);
			TextView t3=(TextView)listViewItem.findViewById(R.id.tvplace);
			TextView t4=(TextView)listViewItem.findViewById(R.id.tvpincode);
			TextView t5=(TextView)listViewItem.findViewById(R.id.tvcperson);
			TextView t6=(TextView)listViewItem.findViewById(R.id.tvrelation);
//			TextView t7=(TextView)listViewItem.findViewById(R.id.tvphone);


	        t1.setText("Missing Person : " + pname[position]);
			t2.setText("House Name : " + hname[position]);
			t3.setText("Place : " + place[position]);
			t4.setText("Pincode : " + pincode[position]);
			t5.setText("Contact Person : " + cperson[position]);
			t6.setText("Relation : " + relation[position]);
//			t7.setText("Contact Number : " + phone[position]);


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