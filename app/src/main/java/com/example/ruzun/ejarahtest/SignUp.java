package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextView LogInTextView;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextName;

    private Button signUpButton;

    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Context context;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = this;

        //---to enable navigation to Log In activity if user want to log in ----
        LogInTextView = (TextView) findViewById(R.id.textViewLogIn);
        LogInTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });
        //---------------------------------------------------------------------------------//

        editTextEmail  = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextName  = (EditText) findViewById(R.id.editTextName);


        mFirebaseAuth = FirebaseAuth.getInstance();

        signUpButton = (Button) findViewById(R.id.buttonSignUp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String confirmPassword = editTextConfirmPassword.getText().toString();
                final String name = editTextName.getText().toString();


                if (email.isEmpty()) {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(context, "please enter password", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context, "Please fill in the blank fields", Toast.LENGTH_SHORT).show();
                }
                else if (!confirmPassword.equals(password))
                    Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show();
                else if (!(email.isEmpty() && password.isEmpty())) {


                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            else {
                                user = new User(email,name);
                                String key = databaseReference.child("User").push().getKey();
                                user.setUserID(key);
                                databaseReference.child("User").child(key).setValue(user);
                                Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, Timeline.class));

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
