package com.example.bloodprojectapplication.ui.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodprojectapplication.R;
import com.example.bloodprojectapplication.domain.SharePreference;
import com.example.bloodprojectapplication.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView backButton;

    private TextInputEditText loginEmailAddress, loginPassword;
    private TextView forgotPassword;

    private Button loginButton;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        };

        loginEmailAddress = findViewById(R.id.loginEmailAddress);
        loginPassword = findViewById(R.id.loginPassword);
        forgotPassword = findViewById(R.id.forgotPassword);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);

        loader = new ProgressDialog(this);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = loginEmailAddress.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    loginEmailAddress.setError("Email is required");
                }

                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is required");
                } else {
                    loader.setMessage("log in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                                getUserDetails();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                            loader.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void getUserDetails(){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        userRef.get().addOnCompleteListener(task -> {
            DataSnapshot snapshot = task.getResult();
            if (snapshot.exists()){
                String name = snapshot.child("name").getValue().toString();
                SharePreference.getINSTANCE(getApplicationContext()).setName(name);


                String email = snapshot.child("email").getValue().toString();
                SharePreference.getINSTANCE(getApplicationContext()).setEmail(email);


                String bloodGroup = snapshot.child("bloodgroup").getValue().toString();
                SharePreference.getINSTANCE(getApplicationContext()).setBloodgroup(bloodGroup);


                String type = snapshot.child("type").getValue().toString();
                SharePreference.getINSTANCE(getApplicationContext()).setType(type);

                String phoneNumber = snapshot.child("phonenumber").getValue().toString();
                SharePreference.getINSTANCE(getApplicationContext()).setPhonenumber(phoneNumber);

            }

        });
    }
}