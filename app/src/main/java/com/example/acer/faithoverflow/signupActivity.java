package com.example.acer.faithoverflow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupActivity extends AppCompatActivity {
    private EditText txtEmailAddress;
    private EditText txtPassword, txtPassword2;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        txtEmailAddress = (EditText) findViewById(R.id.editText3);
        txtPassword = (EditText) findViewById(R.id.editText4);
        txtPassword2=findViewById(R.id.editText5);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Signup_Click(View v) {
        if(txtPassword.getText().toString().equals(txtPassword2.getText().toString())) {
            final ProgressDialog progressDialog = ProgressDialog.show(signupActivity.this, "Please wait...", "Processing...", true);
            (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(signupActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(signupActivity.this, loginActivity.class);
                                startActivity(i);
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                registrationUnsuccessful("Registration Unsuccessful");

                            }
                        }
                    });
        }
        else
            registrationUnsuccessful("Passwords dont match");
    }
    private void registrationUnsuccessful(String message)
    {
        new AlertDialog.Builder(this)
                .setTitle("OOPS!!")
                .setMessage(message)
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent m = new Intent(signupActivity.this, signupActivity.class);
                        finish();
                        startActivity(m);
                    }
                })
                .setCancelable(false)
                .show();

    }
}