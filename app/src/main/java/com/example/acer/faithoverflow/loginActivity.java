package com.example.acer.faithoverflow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity
{
    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtEmailLogin = (EditText) findViewById(R.id.email);
        txtPwd = (EditText) findViewById(R.id.editText);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Login_Click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(loginActivity.this, "Please wait...", "Proccessing...", true);
        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        FirebaseUser u = firebaseAuth.getCurrentUser();

                        if (task.isSuccessful()&&u.isEmailVerified()) {
                            Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(loginActivity.this, MainActivity.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(loginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    public void signup(View view)
    {
        Intent i=new Intent(loginActivity.this,signupActivity.class);
        startActivity(i);
    }
}
