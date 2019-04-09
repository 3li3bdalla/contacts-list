package com.contacts;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class details extends AppCompatActivity {

    SQLiteDatabase db;
    DbHelper dbHelper;
    EditText name_edittext, phone_edittext;

    TextView name_textview,showupdate;

    Button update,call;

    LinearLayout updatelayout;


    String phone="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();


        name_edittext = findViewById(R.id.name);
        phone_edittext = findViewById(R.id.phone);

        update = findViewById(R.id.update);
        call = findViewById(R.id.call);

        showupdate = findViewById(R.id.show_update);
        name_textview = findViewById(R.id.name_textview);


        updatelayout = findViewById(R.id.updatelayout);
















        Cursor cursor = db.rawQuery("select * from " + dbHelper.CONTACT_TABLE_NAME + " where " + dbHelper.ID + "=" + id, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {


                this.setTitle(cursor.getString(cursor.getColumnIndex(dbHelper.NAME)));
                name_textview.setText(cursor.getString(cursor.getColumnIndex(dbHelper.NAME)));

                name_edittext.setText(cursor.getString(cursor.getColumnIndex(dbHelper.NAME)));

                phone_edittext.setText(cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_NUMBER)));

                 phone=cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_NUMBER));



            }
            cursor.close();

        }

//
          update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ContentValues cv = new ContentValues();
                cv.put(DbHelper.NAME, name_edittext.getText().toString());
                cv.put(DbHelper.PHONE_NUMBER,  phone_edittext.getText().toString());



                db.update(dbHelper.CONTACT_TABLE_NAME, cv, "_id=" + id, null);

                Toast.makeText(details.this, "updated ", Toast.LENGTH_SHORT).show();



            }
        });




        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            callphone(phone);


            }
        });
        showupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                updatelayout.setVisibility(View.VISIBLE);




            }
        });
    }



    private  void callphone(String number){

        if (ContextCompat.checkSelfPermission(details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(details.this, new String[]{Manifest.permission.CALL_PHONE},99);



        }
        else
        {



            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +number));// Initiates the Intent
            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }



}
