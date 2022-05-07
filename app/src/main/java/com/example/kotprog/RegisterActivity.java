package com.example.kotprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText secondPasswordEditText;

    private FirebaseAuth mAuth;

    private NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.usernameEditText = findViewById(R.id.editTextLicensePlate);
        this.emailEditText = findViewById(R.id.editTextBrand);
        this.passwordEditText = findViewById(R.id.editTextOwner);
        this.secondPasswordEditText = findViewById(R.id.editTextSchedule);

        this.mNotificationHandler = new NotificationHandler(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String userName = this.usernameEditText.getText().toString();
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        String password2 = this.secondPasswordEditText.getText().toString();
        if(!password.equals(password2)){
            Toast.makeText(RegisterActivity.this, "Nem egyezik a két jelszó", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userName.isEmpty() || password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Nem adtál meg minden adatot", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(LOG_TAG,"Felhaszálónév: "+userName + "email:"+ email + "Jelszó: " + password + " password2:" + password2);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Sikeres regisztráció");
                    mNotificationHandler.send("Sikeres regisztráció!");
                    backToMenu(view);
                } else {
                    Log.d(LOG_TAG, "Sikertelen regisztráció" + task.getException().getMessage());
                    Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}