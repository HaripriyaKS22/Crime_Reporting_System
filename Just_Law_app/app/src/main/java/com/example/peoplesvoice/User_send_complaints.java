package com.example.peoplesvoice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;




import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.telephony.TelephonyManager;

public class User_send_complaints extends Activity implements JsonResponse {

    //	 String rid;
    Button b2;
    EditText e1,e2;
    String q;
    ImageButton imgbtn;

//    public String encodedImage = "", path = "";
    public static String labels,pre,des,title,selectedImagePath;
//
//    private final int GALLERY_CODE = 201;
    private final int REQUEST_TAKE_GALLERY_VIDEO = 305;
//    private Uri mImageCaptureUri;
//
    String fln, ftype = "", fname, upLoadServerUri;
//
//    public static byte[] byteArray;
//
//    File f = null;
//
    Button btupim;
//
//    private String imagename = "";
//    public static Bitmap image;
//
//    private static final int CAMERA_CODE = 101,  CROPING_CODE = 301,READ_EXTERNAL_STORAGE_PERMISSION=1,CAMERA=2;


    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_complaints);

        try {
            if(Build.VERSION.SDK_INT>9){
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) { }


        btupim=(Button)findViewById(R.id.btupim);
        b2=(Button)findViewById(R.id.btcmp);
        e1=(EditText)findViewById(R.id.etdes);
        e2=(EditText)findViewById(R.id.ettitle);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),User_view_complaints.class));
            }
        });

        btupim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                des=e1.getText().toString();
                title=e2.getText().toString();

                if (des.isEmpty()) {
                    e1.setError("Please enter the description");
                    return;
                }

                if (title.isEmpty()) {
                    e2.setError("Please enter the title");
                    return;
                }
                else {

                    sendAttach();
                }
            }
        });


        imgbtn=(ImageButton)findViewById(R.id.ibclick);



        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final CharSequence[] items = {"Capture Photo", "Choose from Gallery","Choose From Video", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(User_send_complaints.this);
                builder.setTitle("Add File!");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Capture Photo")) {
                            ftype="image";
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, CAMERA_PIC_REQUEST);

                        } else if (items[item].equals("Choose from Gallery")) {
                            ftype="image";
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, GALLERY_CODE);


                        }
                        else if (items[item].equals("Choose From Video")) {
                            ftype = "video";
                            Intent intent = new Intent();
                            intent.setType("video/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);


                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                //	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i, GALLERY_CODE);
            }
        });
    }



    private void sendAttach() {
        // TODO Auto-generated method stub

        try {
//	            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");



//	        	rid=View_waste_disposal_request.report_id;


             q = "http://" +IPSetting.ip+"/api/user_send_complaints";
//	            String q = "/user_upload_file/?image="+imagename+"&desc="+des+"&yts="+yt;
//	        	String q = "http://" +IPSetting.ip+"/TeachersHelper/api.php?action=teacher_upload_notes";
//	        	String q= "api.php?action=teacher_upload_notes&sh_id="+Teacher_view_timetable.s_id+"&desc="+des;
            Toast.makeText(getApplicationContext(), "Byte Array:"+byteArray.length, Toast.LENGTH_LONG).show();


            Map<String, byte[]> aa = new HashMap<String, byte[]>();

            aa.put("image",byteArray);
//	            aa.put("sh_id",Teacher_view_timetable.s_id.getBytes());
            aa.put("title",title.getBytes());
            aa.put("desc",des.getBytes());
            aa.put("logid",Login.logid.getBytes());
            aa.put("ftype", ftype.getBytes());
            Log.d(q,"");
            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) User_send_complaints.this;
            fua.execute(aa);
            Toast.makeText(getApplicationContext(), "Byte Array:"+aa, Toast.LENGTH_LONG).show();
//	            String data = fua.getResponse();
//	            stopService(new Intent(getApplicationContext(),Capture.class));
//	            Log.d("response=======", data);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imgbtn.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imgbtn.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
//	                selectedImagePath = getPaths(selectedImageUri);
//	                if (selectedImagePath != null) {
//
//	                	Toast.makeText(getApplicationContext(), "Helloo Files", Toast.LENGTH_LONG).show();
//	                    Intent intent = new Intent(User_upload_files.this,
//	                            User_upload_files.class);
//	                    intent.putExtra("path", selectedImagePath);
//	                    startActivity(intent);
//	                }
//	                image = decodeFile(selectedImagePath);
                try {
                    InputStream iStream =   getContentResolver().openInputStream(selectedImageUri);
                    byte[] inputData = getBytes(iStream);
                    Toast.makeText(getApplicationContext(), "Len : " + inputData.length, Toast.LENGTH_LONG).show();
                    byteArray = inputData;
                } catch(Exception e) {}
            }
        }

    }

    public byte[] getBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public Bitmap decodeFile(String path) {//you can provide file path here
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            //    BitmapFactory.Options o = new BitmapFactory.Options();
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 0;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            //  BitmapFactory.Options o2 = new BitmapFactory.Options();
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inPreferredConfig = Bitmap.Config.RGB_565;
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

            //exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                //m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			// Inflate the menu; this adds items to the action bar if it is present.
//			getMenuInflater().inflate(R.menu.citizen__captureimage, menu);
//			return true;
//		}

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Result Found....\n Successfully Updated", Toast.LENGTH_LONG).show();
//		                JSONArray ja = (JSONArray) jo.getJSONArray("data");
//		                labels = ja.getJSONObject(0).getString("label");
//		                pre = ja.getJSONObject(0).getString("precatuions");



                //startActivity(new Intent(getApplicationContext(),Viewmodeldetails.class));
                startActivity(new Intent(getApplicationContext(),User_send_complaints.class));

            }
            else if (status.equalsIgnoreCase("failed")) {

                Toast.makeText(getApplicationContext(),"Image Not Found", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Users_home.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Response Exc : " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }

    // UPDATED!
    public String getPaths(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
}