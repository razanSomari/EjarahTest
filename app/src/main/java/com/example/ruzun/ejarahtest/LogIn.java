package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    TextView signUpTextView;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button logInButton;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    Context context;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //---to enable navigation to Sign Up activity if user want to create new Account ----
        signUpTextView = (TextView) findViewById(R.id.textViewSignUp);
        signUpTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });
        //---------------------------------------------------------------------------------//

        context = this;

        mFirebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        logInButton = (Button) findViewById(R.id.buttonLogIn);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFiberbaseUser != null) {
                    Toast.makeText(LogIn.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogIn.this, MainActivity.class));
                } else
                    Toast.makeText(LogIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        };

        logInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            else{
                                Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
