package com.zcorridor.expensetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Targetlist extends AppCompatActivity {
    RecyclerView recyclerViewtarget;
    private static final int CAMERA_REQUEST =123;
    private static final int SELECT_IMAGE =1 ;
    AlertDialog dialog,dialogimagepicker;
    ImageView ivtakephoto;
    TextView tvdes,tvamnt;
    String realpath;
    Sqlitetarget mydb;
    ArrayList<Targetmodalclass> list;
    Targetadapter adapter;
    TextView textViewgoalsavedamnt;
    AdView adView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_targetlist);
        recyclerViewtarget = findViewById(R.id.recyclerviewtarget);
        textViewgoalsavedamnt = findViewById(R.id.totalsavinggoals);
        mydb = new Sqlitetarget(this,Sqlitetarget.DATABASE_NAME,null,1);
        list = new ArrayList<Targetmodalclass>();
        adapter = new Targetadapter(this,list);
        recyclerViewtarget.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewtarget.setAdapter(adapter);
        refrsh();
        adView4=findViewById(R.id.adView4);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView4.loadAd(adRequest);
      }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView4!=null){
            adView4.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(adView4!=null){
            adView4.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView4!=null){
            adView4.resume();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        refrsh();
     }
     public void refrsh(){
       list.clear();
         list.addAll(mydb.getTargetData());
         int goals=0;
         for (int i=0;i<list.size();i++){
             int x= list.get(i).getAmountspent();
             goals=goals+x;
         }
         textViewgoalsavedamnt.setText(goals+"");
         adapter.notifyDataSetChanged();
     }

    public void addtTarget(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view1= LayoutInflater.from(this).inflate(R.layout.targetaddrows,null);
        tvdes = view1.findViewById(R.id.tvdes);
        tvamnt = view1.findViewById(R.id.tvamnt);
        builder.setView(view1);
        dialog =builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ivtakephoto = view1.findViewById(R.id.ivtakephoto);
        ivtakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view1.getContext());
               View view2= LayoutInflater.from(view1.getContext()).inflate(R.layout.imagepicker,null);
                builder1.setView(view2);
                dialogimagepicker=builder1.create();
                dialogimagepicker.show();
                TextView tvglry = (TextView) view2.findViewById(R.id.tvgallery);
                tvglry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"galeryimage"),SELECT_IMAGE);
                    }
                });
                TextView tvcmra = (TextView) view2.findViewById(R.id.tvcamera);
                tvcmra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,CAMERA_REQUEST);
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SELECT_IMAGE && resultCode== Activity.RESULT_OK && data!=null){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), data.getData());
                ivtakephoto.setImageBitmap(bitmap);
                dialogimagepicker.dismiss();
                data.getData();
                Uri uri = data.getData();
                realpath = getPath(Targetlist.this, getImageUri(this, bitmap));
                Toast.makeText(this, "path is:"+realpath, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        else if
        (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            String result = data.toURI();
            Uri uri = data.getData();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            realpath = getPath(this, getImageUri(this, bitmap));
            Toast.makeText(this, "file path is" + realpath, Toast.LENGTH_SHORT).show();
             ivtakephoto.setImageBitmap(bitmap);
             dialogimagepicker.dismiss();
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplication(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    private Uri getImageUri(Targetlist targetlist, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(targetlist.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public void savetarget(View view) {
        if (!(tvdes.getText().toString().isEmpty()) && !(tvamnt.getText().toString().isEmpty()) && realpath != null) {
            tvdes.getText().toString();
            tvamnt.getText().toString();
            boolean res = mydb.insertTargetData(tvdes.getText().toString(),
                    Integer.parseInt(tvamnt.getText().toString()),
                    realpath,0,0L);
            if (res == true) {
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                refrsh();
                dialog.dismiss();
                realpath=null;
            } else
                Toast.makeText(this, "DAta Inserted Failed", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
    }

    public void showRecord(View view) {
        Intent intent=new Intent(Targetlist.this, Transactionrecord.class);
        startActivity(intent);
    }
}