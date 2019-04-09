package com.contacts;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextName, editTextSchool;

    SQLiteDatabase db;
    DbHelper mDbHelper;


    int spending =0,income=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);



        findViewById(R.id.buttonLogin).setOnClickListener(this);


        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void signin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }



        if (mDbHelper.Userlogin(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {

//
            Intent accountsIntent = new Intent(login.this, MainActivity.class);
            startActivity(accountsIntent);


            SharedPrefManager.getInstance(login.this)
                    .savelogin(email);


            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();



            // SHOW NOTIFICATION


                makeNotification("Contact List","Welcome");

                //  cursor2.close();





        } else {
            // Snack Bar to show success amount that record is wrong

            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();


        }



    }


    public  void makeNotification(String title,String content) {



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder( getApplicationContext())
                        .setSmallIcon(R.drawable.ic_action_alarms)
                        .setContentTitle(title)
                        .setContentText(content).setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" +  getApplicationContext().getPackageName() + "/raw/notify"));

        // Add as notification
        NotificationManager manager = (NotificationManager)  getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                signin();
                break;


        }
    }

    public void openreg(View view) {


        Intent Intent = new Intent(login.this, register.class);
        startActivity(Intent);
    }
}