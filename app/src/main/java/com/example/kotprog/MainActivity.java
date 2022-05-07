package com.example.kotprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton,registerButton;
    Animation scaleUp,scaleDown;

    private FirebaseAuth mAuth;

    private NotificationHandler mNotificationHandler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.emailEditText = findViewById(R.id.editTextEmail);
        this.passwordEditText = findViewById(R.id.editTextPassword);
        this.loginButton = findViewById(R.id.loginButton);
        this.registerButton = findViewById(R.id.registerButton);

        this.scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        this.scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        this.loginButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    loginButton.startAnimation(scaleUp);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    loginButton.startAnimation(scaleDown);
                    login();
                }
                return true;
            }
        });

        this.registerButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    registerButton.startAnimation(scaleUp);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    registerButton.startAnimation(scaleDown);
                    register();
                }
                return true;
            }
        });

        this.mNotificationHandler = new NotificationHandler(this);


        mAuth = FirebaseAuth.getInstance();
    }

    public void login() {

        String username = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        Log.i(LOG_TAG,"Felhaszálónév: "+username + "Jelszó: " + password);
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, "Nem adtál meg minden adatot", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Sikeres login");
                    mNotificationHandler.cancel();
                    toCarInsurances();
                } else {
                    Log.d(LOG_TAG, "Sikertelen bejelentkezés" + task.getException().getMessage());
                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void toCarInsurances(){
        Intent intent = new Intent(this, CarInsuranceActivity.class);
        startActivity(intent);
    }

    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }
}