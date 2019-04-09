package com.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_contact extends AppCompatActivity {



    SQLiteDatabase db;
    DbHelper mDbHelper;
    EditText name,phone;



    Button buttonsave;

;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
//
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        buttonsave=findViewById(R.id.buttonsave);






        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                // insert into database
                ContentValues cv = new ContentValues();
                cv.put(mDbHelper.NAME, name.getText().toString());
                cv.put(mDbHelper.PHONE_NUMBER, phone.getText().toString());




                db.insert(mDbHelper.CONTACT_TABLE_NAME, null, cv);


                Toast.makeText(add_contact.this, "Saved Successfully", Toast.LENGTH_SHORT).show();




            }
        });




    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }



}
