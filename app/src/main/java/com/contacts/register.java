package com.contacts;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextName, editTextSchool;
    SQLiteDatabase db;
    DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);


        findViewById(R.id.buttonSignUp).setOnClickListener(this);



    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();


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

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (!mDbHelper.checkUser(editTextEmail.getText().toString().trim())) {

            String Name=(editTextName.getText().toString().trim());
            String Email=(editTextEmail.getText().toString().trim());
            String Password=(editTextPassword.getText().toString().trim());

            mDbHelper.addUser(Name,Email,Password);

            // Snack Bar to show success amount that record saved successfully
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

        } else {
            // Snack Bar to show error amount that record already exists
            Toast.makeText(this, "fialed", Toast.LENGTH_SHORT).show();


        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                userSignUp();
                break;

        }
    }
}
